package org.apache.batik.ext.awt.image.rendered;

import  java.awt.image.Raster;

/**
 * This is a Grid based implementation of the TileStore.
 * This makes it pretty quick, but it can use a fair amount of
 * memory for large tile grids.
 */

public class TileGrid implements TileStore {
    private static final boolean DEBUG = false;
    private static final boolean COUNT = false;		

    private int xSz, ySz;
    private int minTileX, minTileY;
    private TileLRUMember   [][] rasters=null;
    private TileGenerator source = null;
    private LRUCache      cache = null;

    public TileGrid(int minTileX, int minTileY,
                    int xSz, int ySz, 
                    TileGenerator source,
                    LRUCache cache) {
        this.cache    = cache;
        this.source   = source;
        this.minTileX = minTileX;
        this.minTileY = minTileY;
        this.xSz      = xSz;
        this.ySz      = ySz;

        rasters = new TileLRUMember[ySz][];
    }

    public void setTile(int x, int y, Raster ras) {
        x-= minTileX;
        y-= minTileY;
        if ((x<0) || (x>=xSz)) return;
        if ((y<0) || (y>=ySz)) return;
		
        TileLRUMember [] row = rasters[y];
        TileLRUMember item;
        if (row != null) {
            item = row[x];
            if (item == null) {
                item = new TileLRUMember();
                row[x] = item;
            }
        } else {
            row = new TileLRUMember[xSz];
            item = new TileLRUMember();
            row[x] = item;
            rasters[y] = row;
        } 
        item.setRaster(ras);
		
        if (item.lruGet() != null) cache.touch(item);
        else                       cache.add(item);

        if (DEBUG) System.out.println("Setting: (" + (x+minTileX) + ", " + 
                                      (y+minTileY) + ")");
    }

    // Returns true if the tile is _currently_ in the cache.  This
    // may not be true by the time you get around to calling
    // getTile however...
    public boolean checkTile(int x, int y) {
        x-=minTileX;
        y-=minTileY;
        if ((x<0) || (x>=xSz)) return false;
        if ((y<0) || (y>=ySz)) return false;

        TileLRUMember [] row = rasters[y];
        if (row == null)
            return false;
        TileLRUMember item = row[x];
        if (item == null)
            return false;
        return item.checkRaster();
    }

    public Raster getTile(int x, int y) {
        x-=minTileX;
        y-=minTileY;
        if ((x<0) || (x>=xSz)) return null;
        if ((y<0) || (y>=ySz)) return null;

        if (DEBUG) System.out.println("Fetching: (" + (x+minTileX) + ", " + 
                                      (y+minTileY) + ")");
        if (COUNT) synchronized (TileGrid.class) { requests++; }

        Raster       ras  = null;
        TileLRUMember [] row  = rasters[y];
        TileLRUMember    item = null;
        if (row != null) {
            item = row[x];
            if (item != null)
                ras = item.retrieveRaster();
            else {
                item = new TileLRUMember();
                row[x] = item;
            }
        } else {
            row = new TileLRUMember[xSz];
            rasters[y] = row;
            item = new TileLRUMember();
            row[x] = item;
        }

        if (ras == null) {
            if (DEBUG) System.out.println("Generating: ("+(x+minTileX)+", "+
                                          (y+minTileY) + ")");
            if (COUNT) synchronized (TileGrid.class) { misses++; }
            ras = source.genTile(x+minTileX, y+minTileY);
            item.setRaster(ras);
        }

        // Update the item's position in the cache..
        cache.add(item);

        return ras;
    }

    static int requests;
    static int misses;
}
