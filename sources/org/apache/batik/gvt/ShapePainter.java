/*****************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.apache.batik.gvt;

import java.awt.Shape;
import java.awt.Graphics2D;

/**
 * Renders the shape of a <tt>ShapeNode</tt>.
 *
 * @author <a href="mailto:Thierry.Kormann@sophia.inria.fr">Thierry Kormann</a>
 * @version $Id$
 */
public interface ShapePainter {

    /**
     * Paints the specified shape using the specified Graphics2D.
     *
     * @param g2d the Graphics2D to use
     */
    void paint(Graphics2D g2d, GraphicsNodeRenderContext ctx);

    /**
     * Returns the area painted by this shape painter.
     */
    Shape getPaintedArea();

    /**
     * Sets the Shape this shape painter is associated with.
     *
     * @param shape new shape this painter should be associated with.
     * Should not be null.  
     */
    void setShape(Shape shape);

    /**
     * Gets the shape this shape painter is associated with.
     *
     * @return shape associated with this painter
     */
    Shape getShape();
}
