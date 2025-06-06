/*
 * Copyright (c) 2024 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/>.
 */
package megamek.client.ui.clientGUI.boardview.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import megamek.client.ui.clientGUI.GUIPreferences;
import megamek.client.ui.clientGUI.boardview.BoardView;
import megamek.client.ui.tileset.HexTileset;
import megamek.client.ui.util.StraightArrowPolygon;
import megamek.common.Compute;
import megamek.common.Coords;
import megamek.common.Entity;

/**
 * Sprite and info for movement vector (AT2 advanced movement). Does not
 * actually use the image buffer as this can be horribly inefficient for
 * long diagonal lines.
 *
 * Appears as an arrow pointing to the hex this entity will move to based on
 * current movement vectors.
 *
 * TODO: Different color depending upon whether
 * entity has already moved this turn
 */
public class MovementSprite extends Sprite {

    private Point a;
    private Point t;
    private double an;
    private StraightArrowPolygon movePoly;
    private Color moveColor;

    // private MovementVector mv;
    private int[] vectors;

    private Coords start;
    private Coords end;
    private Entity en;
    private int vel;

    private static final GUIPreferences GUIP = GUIPreferences.getInstance();

    public MovementSprite(BoardView boardView1, Entity e, int[] v, Color col, boolean isCurrent) {
        // this.mv = en.getMV();

        super(boardView1);
        en = e;
        vectors = v;// en.getVectors();
        // get the starting and ending position
        start = en.getPosition();
        end = Compute.getFinalPosition(start, vectors);

        // what is the velocity
        vel = 0;
        for (int element : v) {
            vel += element;
        }

        // color?
        // player colors
        moveColor = en.getOwner().getColour().getColour();
        // TODO: Its not going transparent. Oh well, it is a minor issue at
        // the moment
        /*
         * if (isCurrent) { int colour = col.getRGB(); int transparency =
         * GUIPreferences.getInstance().getInt(GUIPreferences.
         * ADVANCED_ATTACK_ARROW_TRANSPARENCY); moveColor = new Color(colour
         * | (transparency << 24), true); }
         */
        // red if offboard
        if (!this.bv.game.getBoard().contains(end)) {
            int colour = 0xff0000; // red
            int transparency = GUIP.getAttackArrowTransparency();
            moveColor = new Color(colour | (transparency << 24), true);
        }
        // dark gray if done
        if (en.isDone()) {
            int colour = 0x696969; // gray
            int transparency = GUIP.getAttackArrowTransparency();
            moveColor = new Color(colour | (transparency << 24), true);
        }

        // moveColor = PlayerColors.getColor(en.getOwner().getColorIndex());
        // angle of line connecting two hexes
        an = (start.radian(end) + (Math.PI * 1.5)) % (Math.PI * 2); // angle
        makePoly();

        // set bounds
        bounds = new Rectangle(movePoly.getBounds());
        bounds.setSize(bounds.getSize().width + 1,
                bounds.getSize().height + 1);
        // move poly to upper right of image
        movePoly.translate(-bounds.getLocation().x, -bounds.getLocation().y);

        // nullify image
        image = null;
    }

    private void makePoly() {
        // make a polygon
        a = bv.getHexLocation(start);
        t = bv.getHexLocation(end);
        // OK, that is actually not good. I do not like hard coded figures.
        // HEX_W/2 - x distance in pixels from origin of hex bounding box to
        // the center of hex.
        // HEX_H/2 - y distance in pixels from origin of hex bounding box to
        // the center of hex.
        // 18 - is actually 36/2 - we do not want arrows to start and end
        // directly
        // in the centes of hex and hiding mek under.

        a.x = a.x + (int) ((HexTileset.HEX_W / 2) * bv.getScale())
                + (int) Math.round(Math.cos(an) * (int) (18 * bv.getScale()));
        t.x = (t.x + (int) ((HexTileset.HEX_W / 2) * bv.getScale()))
                - (int) Math.round(Math.cos(an) * (int) (18 * bv.getScale()));
        a.y = a.y + (int) ((HexTileset.HEX_H / 2) * bv.getScale())
                + (int) Math.round(Math.sin(an) * (int) (18 * bv.getScale()));
        t.y = (t.y + (int) ((HexTileset.HEX_H / 2) * this.bv.getScale()))
                - (int) Math.round(Math.sin(an) * (int) (18 * bv.getScale()));
        movePoly = new StraightArrowPolygon(a, t, (int) (4 * bv.getScale()),
                (int) (8 * bv.getScale()), false);
    }

    @Override
    public Rectangle getBounds() {
        makePoly();
        // set bounds
        bounds = new Rectangle(movePoly.getBounds());
        bounds.setSize(bounds.getSize().width + 1,
                bounds.getSize().height + 1);
        // move poly to upper right of image
        movePoly.translate(-bounds.getLocation().x, -bounds.getLocation().y);

        return bounds;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void drawOnto(Graphics g, int x, int y, ImageObserver observer) {
        // don't draw anything if the unit has no velocity

        if (vel == 0) {
            return;
        }

        Polygon drawPoly = new Polygon(movePoly.xpoints, movePoly.ypoints,
                movePoly.npoints);
        drawPoly.translate(x, y);

        g.setColor(moveColor);
        g.fillPolygon(drawPoly);
        g.setColor(Color.gray.darker());
        g.drawPolygon(drawPoly);

    }

    /**
     * Return true if the point is inside our polygon
     */
    @Override
    public boolean isInside(Point point) {
        return movePoly.contains(point.x - bounds.x, point.y - bounds.y);
    }
}
