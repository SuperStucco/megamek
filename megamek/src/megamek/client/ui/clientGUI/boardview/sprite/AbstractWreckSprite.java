/*
 * Copyright (c) 2020-2021 - The MegaMek Team. All Rights Reserved.
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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.MessageFormat;

import megamek.client.ui.Messages;
import megamek.client.ui.clientGUI.boardview.BoardView;
import megamek.client.ui.tileset.HexTileset;
import megamek.client.ui.util.EntityWreckHelper;
import megamek.common.*;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.ImageUtil;

/**
 * Contains common functionality for wreck sprites (currently isometric and regular)
 * @author NickAragua
 */
public abstract class AbstractWreckSprite extends Sprite {
    protected Entity entity;

    protected Rectangle modelRect;

    protected int secondaryPos;

    public AbstractWreckSprite(BoardView boardView1) {
        super(boardView1);
    }

    @Override
    public Rectangle getBounds() {
        // Start with the hex and add the label
        bounds = new Rectangle(0, 0, bv.getHexSize().width, bv.getHexSize().height);

        // Move to board position, save this origin for correct drawing
        Point hexOrigin = bounds.getLocation();
        Point ePos;
        if ((secondaryPos < 0) || (secondaryPos >= entity.getSecondaryPositions().size())) {
            ePos = bv.getHexLocation(entity.getPosition());
        } else {
            ePos = bv.getHexLocation(entity.getSecondaryPositions().get(secondaryPos));
        }
        bounds.setLocation(hexOrigin.x + ePos.x, hexOrigin.y + ePos.y);

        return bounds;
    }

    public Entity getEntity() {
        return entity;
    }

    /**
     * Creates the sprite for this entity. It is an extra pain to create
     * transparent images in AWT.
     */
    @Override
    public void prepare() {
        if (entity.getPosition() != null && entity.getGame() != null
            && entity.getGame().getHexOf(entity) != null &&
            entity.getGame().getHexOf(entity).containsTerrain(Terrains.ULTRA_SUBLEVEL)) {
           return; //Don't show wrecks for "Ultra Sublevels" - The unit fell through the map!
        }

        // create image for buffer
        image = ImageUtil.createAcceleratedImage(HexTileset.HEX_W, HexTileset.HEX_H);
        Graphics2D graph = (Graphics2D) image.getGraphics();

        // if the entity is underwater or would sink underwater, we want to make the wreckage translucent
        // so it looks like it sunk
        boolean entityIsUnderwater = (entity.relHeight() < 0) ||
                ((entity.relHeight() >= 0) && entity.getGame().getHexOf(entity).containsTerrain(Terrains.WATER)) &&
                !EntityWreckHelper.entityOnBridge(entity);

        if (entityIsUnderwater) {
            graph.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.35f));
        }

        // draw the 'destroyed decal' where appropriate
        boolean displayDestroyedDecal = EntityWreckHelper.displayDestroyedDecal(entity);

        if (displayDestroyedDecal) {
            Image destroyed = bv.getTileManager().bottomLayerWreckMarkerFor(entity, 0);
            if (null != destroyed) {
                graph.drawImage(destroyed, 0, 0, this);
            }
        }

        // draw the 'fuel leak' decal where appropriate
        boolean drawFuelLeak = EntityWreckHelper.displayFuelLeak(entity);

        if (drawFuelLeak) {
            Image fuelLeak = bv.getTileManager().bottomLayerFuelLeakMarkerFor(entity);
            if (null != fuelLeak) {
                graph.drawImage(fuelLeak, 0, 0, this);
            }
        }

        // draw the 'tires' or 'tracks' decal where appropriate
        boolean drawMotiveWreckage = EntityWreckHelper.displayMotiveDamage(entity);

        if (drawMotiveWreckage) {
            Image motiveWreckage = bv.getTileManager().bottomLayerMotiveMarkerFor(entity);
            if (null != motiveWreckage) {
                graph.drawImage(motiveWreckage, 0, 0, this);
            }
        }

        // Draw wreck image, if we've got one.
        Image wreck;

        if (EntityWreckHelper.displayDevastation(entity)) {
            // objects in space should not have craters
            wreck = entity.isSpaceborne() ?
                    bv.getTileManager().wreckMarkerFor(entity, secondaryPos) :
                     bv.getTileManager().getCraterFor(entity, secondaryPos);
        } else {
            wreck = EntityWreckHelper.useExplicitWreckImage(entity) ?
                         bv.getTileManager().wreckMarkerFor(entity, secondaryPos) :
                         bv.getTileManager().imageFor(entity, secondaryPos);
        }

        if (null != wreck) {
            graph.drawImage(wreck, 0, 0, this);
        }

        if (entityIsUnderwater) {
            graph.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 1.0f));
        }

        // create final image
        image = bv.getScaledImage(image, false);
        graph.dispose();
    }

    /**
     * Overrides to provide for a smaller sensitive area.
     */
    @Override
    public boolean isInside(Point point) {
        return false;
    }

    public Coords getPosition() {
        if (secondaryPos < 0 || secondaryPos >= entity.getSecondaryPositions().size()) {
            return entity.getPosition();
        } else {
            return entity.getSecondaryPositions().get(secondaryPos);
        }
    }

    @Override
    public StringBuffer getTooltip() {
        StringBuffer result = new StringBuffer();
        result.append(Messages.getString("BoardView1.Tooltip.Wreckof") + " ");
        result.append(entity.getChassis());
        result.append(MessageFormat.format(" ({0})", entity.getOwner().getName()));
        if (PreferenceManager.getClientPreferences().getShowUnitId()) {
            result.append(MessageFormat.format(" [ID: {0}]", entity.getId()));
        }
        return result;
    }
}
