/*****************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.apache.batik.gvt.font;

import java.awt.font.GlyphMetrics;
import java.awt.geom.Rectangle2D;

/**
 * GVTGlyphMetrics is essentially a wrapper class for java.awt.font.GlyphMetrics
 * with the addition of horizontal and vertical advance values.
 *
 * @author <a href="mailto:bella.robinson@cmis.csiro.au">Bella Robinson</a>
 * @version $Id$
 */
public class GVTGlyphMetrics {

    private GlyphMetrics gm;
    private float verticalAdvance;

    /**
     * Constructs a new GVTGlyphMetrics object based upon the specified
     * GlyphMetrics object and an additional vertical advance value.
     *
     * @param gm The glyph metrics.
     * @param verticalAdvance The vertical advance of the glyph.
     */
    public GVTGlyphMetrics(GlyphMetrics gm, float verticalAdvance) {
        this.gm = gm;
        this.verticalAdvance = verticalAdvance;
    }

    /**
     * Constructs a new GVTGlyphMetrics object using the specified parameters.
     *
     * @param horizontalAdvance The horizontal advance of the glyph.
     * @param verticalAdvance The vertical advance of the glyph.
     * @param bounds The black box bounds of the glyph.
     * @param glyphType The type of the glyph.
     */
    public GVTGlyphMetrics(float horizontalAdvance, 
			   float verticalAdvance,
                           Rectangle2D bounds, 
			   byte glyphType) {
        this.gm = new GlyphMetrics(horizontalAdvance, bounds, glyphType);
        this.verticalAdvance = verticalAdvance;
    }

    /**
     * Returns the horizontal advance of the glyph.
     */
    public float getHorizontalAdvance() {
        return gm.getAdvance();
    }

    /**
     * Returns the vertical advance of the glyph.
     */
    public float getVerticalAdvance() {
        return verticalAdvance;
    }

    /**
     * Returns the black box bounds of the glyph.
     */
    public Rectangle2D getBounds2D() {
        return gm.getBounds2D();
    }

    /**
     * Returns the left (top) side bearing of the glyph.
     */
    public float getLSB() {
        return gm.getLSB();
    }

    /**
     * Returns the right (bottom) side bearing of the glyph.
     */
    public float getRSB() {
        return gm.getRSB();
    }

    /**
     * Returns the raw glyph type code.
     */
    public int getType() {
        return gm.getType();
    }

    /**
     * Returns true if this is a combining glyph.
     */
    public boolean isCombining() {
        return gm.isCombining();
    }

    /**
     * Returns true if this is a component glyph.
     */
    public boolean isComponent() {
        return gm.isComponent();
    }

    /**
     * Returns true if this is a ligature glyph.
     */
    public boolean isLigature() {
        return gm.isLigature();
    }

    /**
     * Returns true if this is a standard glyph.
     */
    public boolean isStandard() {
        return gm.isStandard();
    }

    /**
     * Returns true if this is a whitespace glyph.
     */
    public boolean isWhitespace() {
        return gm.isWhitespace();
    }

}
