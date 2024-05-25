/*
 * MegaMek - Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package megamek.client.ratgenerator;

import java.util.Collection;
import java.util.HashSet;

import megamek.common.EntityMovementMode;
import megamek.common.EntityWeightClass;
import megamek.common.UnitType;

/**
 * Used to adjust availability to conform to a particular mission role.
 *
 * @author Neoancient
 */
public enum MissionRole {
    /*General combat roles */
    RECON, RAIDER, INCENDIARY, EW_SUPPORT, ARTILLERY, MISSILE_ARTILLERY, APC, TRAINING, COMMAND,
    /* Non-combat roles */
    CARGO, SUPPORT, CIVILIAN,
    /* Ground forces */
    FIRE_SUPPORT, SR_FIRE_SUPPORT, URBAN, SPOTTER, ANTI_AIRCRAFT, ANTI_INFANTRY, INF_SUPPORT, CAVALRY,
    /* Specialized ground support roles */
    SPECOPS, ENGINEER, MINESWEEPER, MINELAYER,
    /* ASF roles */
    BOMBER, ESCORT, INTERCEPTOR, GROUND_SUPPORT,
    /* DropShip roles */
    ASSAULT, MECH_CARRIER, ASF_CARRIER, VEE_CARRIER, INFANTRY_CARRIER, BA_CARRIER, TROOP_CARRIER,
    TUG, POCKET_WARSHIP, PROTOMECH_CARRIER,
    /* WarShip roles */
    CORVETTE, DESTROYER, FRIGATE, CRUISER, BATTLESHIP,
    /* Mechanized Battle armor */
    OMNI, MECHANIZED_BA, MAG_CLAMP,
    /* Infantry roles */
    MARINE, MOUNTAINEER, XCT, PARATROOPER, ANTI_MEK, FIELD_GUN,
    /* allows artillery but does not filter out all other roles */
    MIXED_ARTILLERY;

    public boolean fitsUnitType(int unitType) {
        switch (this) {
            // RECON applies to all unit types except gun emplacements, JumpShips,
            // space stations, and some specialized aerospace
            case RECON:
                return unitType != UnitType.GUN_EMPLACEMENT &&
                        unitType != UnitType.JUMPSHIP &&
                        unitType != UnitType.SPACE_STATION &&
                        unitType != UnitType.AERO;

            // EW_SUPPORT role applies to all ground units, VTOL, blue water naval, gun emplacement,
            // and small craft.  Infantry and large space craft are excluded.
            case EW_SUPPORT:
                return unitType <= UnitType.TANK ||
                        unitType == UnitType.PROTOMEK ||
                        unitType == UnitType.VTOL ||
                        unitType == UnitType.NAVAL ||
                        unitType == UnitType.GUN_EMPLACEMENT ||
                        unitType == UnitType.SMALL_CRAFT;

            // SPOTTER role applies to all ground units plus VTOL, blue water naval, gun
            // emplacements, and fixed wing aircraft.
            case SPOTTER:
                return unitType <= UnitType.AEROSPACEFIGHTER;

            // COMMAND role applies to all ground units, VTOLs, blue water naval, conventional
            // fixed wing aircraft, and small craft.  Conventional infantry, battle armor,
            // ProtoMechs, gun emplacements, and large space vessels are excluded.
            case COMMAND:
                return unitType <= UnitType.TANK ||
                        unitType == UnitType.VTOL ||
                        unitType == UnitType.NAVAL ||
                        unitType == UnitType.CONV_FIGHTER ||
                        unitType == UnitType.SMALL_CRAFT;

            // Fire support roles apply to most types except fixed wing aircraft and space craft
            case FIRE_SUPPORT:
            case SR_FIRE_SUPPORT:
                return unitType <= UnitType.GUN_EMPLACEMENT;

            // Artillery roles apply to all ground units, VTOL, blue water naval, gun emplacements,
            // and conventional fighters.  Small craft and DropShips, which are capable of mounting
            // artillery type weapons, are included.  ProtoMechs cannot carry any existing artillery
            // weapons so are excluded.
            case ARTILLERY:
            case MISSILE_ARTILLERY:
            case MIXED_ARTILLERY:
                return unitType <= UnitType.INFANTRY ||
                        unitType == UnitType.VTOL ||
                        unitType == UnitType.NAVAL ||
                        unitType == UnitType.GUN_EMPLACEMENT ||
                        unitType == UnitType.CONV_FIGHTER;

            // URBAN role applies to all ground units. Although infantry are inherently urban-
            // oriented this role should be reserved for mechanized (wheeled) and others which
            // are not optimized for non-urban terrain.
            case URBAN:
                return unitType <= UnitType.PROTOMEK;

            // Infantry support roles are limited to ground units and VTOLs. This includes infantry
            // and battle armor that are armed primarily with anti-infantry weapons.
            case ANTI_INFANTRY:
            case INF_SUPPORT:
                return unitType <= UnitType.PROTOMEK || unitType == UnitType.VTOL;

            // APC role is limited to units which can carry conventional infantry.  Although blue
            // water naval units can carry infantry they have limited use so are excluded.
            case APC:
                return unitType == UnitType.TANK || unitType == UnitType.VTOL;

            // Roles for battle armor
            case MECHANIZED_BA:
            case MAG_CLAMP:
                return unitType == UnitType.BATTLE_ARMOR;

            // MARINE role applies to select battle armor and conventional infantry units equipped
            // for space combat
            case MARINE:
                return unitType == UnitType.BATTLE_ARMOR || unitType == UnitType.INFANTRY;

            // Conventional infantry roles
            case MOUNTAINEER:
            case PARATROOPER:
            case ANTI_MEK:
            case FIELD_GUN:
            case XCT:
                return unitType == UnitType.INFANTRY;

            // CAVALRY applies to Mechs, ground vehicles, and ProtoMechs
            case CAVALRY:
                return unitType <= UnitType.TANK || unitType == UnitType.PROTOMEK;

            // RAIDER can be applied to  Mechs, ground vehicles, ProtoMechs, and VTOLs
            case RAIDER:
                return  unitType == UnitType.MEK ||
                        unitType == UnitType.TANK ||
                        unitType == UnitType.PROTOMEK ||
                        unitType == UnitType.VTOL;

            // ANTI_AIRCRAFT role applies to all ground units, plus blue water naval and
            // gun emplacements.  Conventional infantry are included (field guns/artillery) but
            // not battle armor or ProtoMechs.
            case ANTI_AIRCRAFT:
                return unitType == UnitType.MEK ||
                        unitType == UnitType.TANK ||
                        unitType == UnitType.INFANTRY ||
                        unitType == UnitType.NAVAL ||
                        unitType == UnitType.GUN_EMPLACEMENT;

            // INCENDIARY applies to all ground units.  This excludes VTOL, blue water naval,
            // and gun emplacements.
            case INCENDIARY:
                return unitType <= UnitType.PROTOMEK;

            // SPECOPS role applies to Mechs, ground vehicles, conventional infantry, and
            // battle armor.
            case SPECOPS:
                return unitType == UnitType.MEK ||
                        unitType == UnitType.TANK ||
                        unitType == UnitType.INFANTRY ||
                        unitType == UnitType.BATTLE_ARMOR;

            // OMNI applies to all units which are capable of being built to make use of pod-mounted
            // equipment.  This is primarily used to determine suitability for mechanized battle
            // armor but other uses may be added later.
            case OMNI:
                return unitType == UnitType.MEK ||
                        unitType == UnitType.TANK ||
                        unitType == UnitType.AEROSPACEFIGHTER;

            // Roles for conventional and aerospace fighters
            case BOMBER:
            case ESCORT:
            case INTERCEPTOR:
            case GROUND_SUPPORT:
                return unitType == UnitType.CONV_FIGHTER || unitType == UnitType.AEROSPACEFIGHTER;

            // Roles for DropShips
            case ASSAULT:
            case VEE_CARRIER:
            case INFANTRY_CARRIER:
            case BA_CARRIER:
            case MECH_CARRIER:
            case TUG:
            case POCKET_WARSHIP:
            case PROTOMECH_CARRIER:
                return unitType == UnitType.DROPSHIP;

            // Mixed units carrier and aerospace carrier roles apply to DropShips and
            // WarShips
            case TROOP_CARRIER:
            case ASF_CARRIER:
                return unitType == UnitType.DROPSHIP || unitType == UnitType.WARSHIP;

            // Roles for WarShips.  THese are primarily 'class' designations.
            case CORVETTE:
            case DESTROYER:
            case FRIGATE:
            case CRUISER:
            case BATTLESHIP:
                return unitType == UnitType.WARSHIP;

            // TRAINING applies to Mechs, ground vehicles, VTOLs, blue water naval, and conventional
            // fighters.  Infantry, battle armor, ProtoMechs, and gun emplacements are excluded.
            case TRAINING:
                return unitType == UnitType.MEK ||
                        unitType == UnitType.TANK ||
                        unitType == UnitType.VTOL ||
                        unitType == UnitType.NAVAL ||
                        unitType == UnitType.CONV_FIGHTER;

            // ENGINEER applies to ground vehicles and conventional infantry
            case ENGINEER:
                return unitType == UnitType.TANK || unitType == UnitType.INFANTRY;

            // MINESWEEPER and MINELAYER roles apply to ground vehicles, battle armor, and
            // conventional infantry
            case MINESWEEPER:
            case MINELAYER:
                return unitType == UnitType.TANK ||
                        unitType == UnitType.BATTLE_ARMOR ||
                        unitType == UnitType.INFANTRY;

            // SUPPORT applies to all non-combat ground units, VTOLs, blue water naval, conventional
            // fighters, small craft, and some specialized aerospace.  ProtoMechs, gun
            // emplacements, and WarShips are excluded as these are strictly combat units.
            case SUPPORT:
                return unitType != UnitType.PROTOMEK &&
                        unitType != UnitType.GUN_EMPLACEMENT &&
                        unitType != UnitType.WARSHIP;

            // CIVILIAN applies to all non-combat vehicles in civilian service, which includes
            // all ground units, VTOLs, blue water naval, conventional fighters, small craft,
            // DropShips, JumpShips, space stations, and some specialized aerospace. ProtoMechs,
            // gun emplacements, aerospace fighters, and WarShips are excluded as they are strictly
            // combat units.
            case CIVILIAN:
                return unitType != UnitType.PROTOMEK &&
                        unitType != UnitType.GUN_EMPLACEMENT &&
                        unitType != UnitType.AEROSPACEFIGHTER &&
                        unitType != UnitType.WARSHIP;

            // CARGO applies to ground vehicles, VTOLs, blue water naval, conventional fighters,
            // small craft, and all large space vessels.
            case CARGO:
                return unitType == UnitType.TANK ||
                        unitType == UnitType.VTOL ||
                        unitType == UnitType.NAVAL ||
                        unitType == UnitType.CONV_FIGHTER ||
                        (unitType >= UnitType.SMALL_CRAFT && unitType <= UnitType.SPACE_STATION);

            default:
                return false;
        }
    }

    /**
     * Adjusts the provided availability rating based on desired roles, the roles the provided
     * unit has, and other factors such as unit types, movement speed, and weapon types.
     * @param avRating     Availability rating as positive number, may be zero (0)
     * @param desiredRoles Role constants that are desired or mandatory
     * @param mRec         ModelRecord of specific unit to check
     * @param year         Year to test in (unused)
     * @param strictness   Zero or higher, larger values are more restrictive
     * @return             avRating, adjusted for roles provided and present on unit
     */
    public static Double adjustAvailabilityByRole(double avRating,
                                                  Collection<MissionRole> desiredRoles,
                                                  ModelRecord mRec, int year, int strictness) {
        boolean roleApplied = false;
        if (desiredRoles == null) {
            desiredRoles = new HashSet<>();
        }
        double[] avAdj = new double[5];
        for (int i = 0; i < avAdj.length; i++) {
            avAdj[i] = (i + 1) * strictness / 3.0;
        }

        double min_adjust = avAdj[0];
        double light_adjust = avAdj[1];
        double medium_adjust = avAdj[2];
        double strong_adjust = avAdj[3];
        double max_adjust = avAdj[4];

        if (!desiredRoles.isEmpty()) {
            roleApplied = true;
            for (MissionRole role : desiredRoles) {
                switch (role) {
                    case ARTILLERY:
                        if (!mRec.getRoles().contains(ARTILLERY) &&
                                !mRec.getRoles().contains(MISSILE_ARTILLERY) &&
                                !mRec.getRoles().contains(MIXED_ARTILLERY)) {
                            return null;
                        }
                        break;
                    case MISSILE_ARTILLERY:
                        if (!mRec.getRoles().contains(MISSILE_ARTILLERY)) {
                            return null;
                        }
                        break;
                    case MIXED_ARTILLERY:
                        break;
                    case ENGINEER:
                        if (!mRec.getRoles().contains(ENGINEER)) {
                            return null;
                        }
                        break;
                    // Calling for a minelayer should only return those units with that role
                    case MINELAYER:
                        if (!mRec.getRoles().contains(MINELAYER)) {
                            return null;
                        }
                        break;
                    // Calling for a minesweeper should ony return those units with that role
                    case MINESWEEPER:
                        if (!mRec.getRoles().contains(MINESWEEPER)) {
                            return null;
                        }
                        break;
                    case RECON:
                        if (isSpecialized(desiredRoles, mRec)) {
                            return null;
                        } else if (mRec.getRoles().contains(RECON)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(EW_SUPPORT) ||
                                mRec.getRoles().contains(SPECOPS)) {
                            avRating += light_adjust;
                        } else if (mRec.getRoles().contains(SPOTTER)) {
                            avRating += min_adjust;
                        } else {
                            if (mRec.getUnitType() != UnitType.INFANTRY
                                    && mRec.getUnitType() != UnitType.BATTLE_ARMOR
                                        && mRec.getSpeed() < 4 + medium_adjust - mRec.getWeightClass()) {
                                return null;
                            }
                            avRating -= strong_adjust;
                        }
                        break;
                    // Technically any units can be fielded by specops formations, especially
                    // non-infantry types, but those with the role should take priority
                    case SPECOPS:
                        if (mRec.getRoles().contains(SPECOPS)) {
                            avRating += strong_adjust;
                        } else if (mRec.getUnitType() == UnitType.INFANTRY) {
                            avRating -= medium_adjust;
                        } else {
                            avRating -= light_adjust;
                        }
                        break;
                    // Calling for electronic warfare and support units should only return units
                    // which fit the role
                    case EW_SUPPORT:
                        if (!mRec.getRoles().contains(EW_SUPPORT)) {
                            return null;
                        }
                        break;
                    // Technically any unit can spot, but beyond those with the specific role
                    // conventional infantry and battle armor are best
                    case SPOTTER:
                        if (mRec.getRoles().contains(SPOTTER)) {
                            avRating += strong_adjust;
                        } else if (mRec.getUnitType() == UnitType.INFANTRY ||
                                mRec.getUnitType() == UnitType.BATTLE_ARMOR) {
                            avRating += light_adjust;
                        } else {
                            avRating -= medium_adjust;
                        }
                        break;
                    case COMMAND:
                        if (mRec.getRoles().contains(COMMAND)) {
                            avRating += medium_adjust;
                        }
                        if ((ModelRecord.NETWORK_COMPANY_COMMAND & mRec.getNetworkMask()) != 0) {
                            avRating += light_adjust;
                        } else if ((ModelRecord.NETWORK_C3_MASTER & mRec.getNetworkMask()) != 0) {
                            avRating += min_adjust;
                        }
                        break;
                    // Calling for armored personnel carriers should only return units which have
                    // that role
                    case APC:
                        if (!mRec.getRoles().contains(APC)) {
                            return null;
                        }
                        break;
                    // Infantry with anti-mech equipment should be prioritized for the role, but
                    // non-mechanized types can also be used
                    case ANTI_MEK:
                        if (mRec.getRoles().contains(ANTI_MEK)) {
                            avRating += medium_adjust;
                        } else if (mRec.getMovementMode() == EntityMovementMode.TRACKED ||
                                    mRec.getMovementMode() == EntityMovementMode.WHEELED ||
                                    mRec.getMovementMode() == EntityMovementMode.HOVER ||
                                    mRec.getMovementMode() == EntityMovementMode.VTOL) {
                                return null;
                        } else {
                            avRating += min_adjust;
                        }
                        break;
                    case FIELD_GUN:
                        if (!mRec.getRoles().contains(FIELD_GUN)) {
                            return null;
                        }
                        break;
                    // Calling for infantry which can operate in low gravity means only those with
                    // the role should be assigned
                    case MARINE:
                        if (!mRec.getRoles().contains(MARINE)) {
                            return null;
                        }
                        break;
                    // Calling for mountaineer-trained infantry requires only those with the
                    // appropriate role
                    case MOUNTAINEER:
                        if (!mRec.getRoles().contains(MOUNTAINEER)) {
                            return null;
                        }
                        break;
                    // Calling for conventional infantry which can be air dropped means only those
                    // with the paratrooper role or jump infantry should be assigned.
                    case PARATROOPER:
                        if (mRec.getRoles().contains(PARATROOPER)){
                            avRating += strong_adjust;
                        } else {
                            if (mRec.getMovementMode() != EntityMovementMode.INF_JUMP) {
                                return null;
                            } else {
                                avRating -= min_adjust;
                            }
                        }
                        break;
                    // Calling for infantry which can operate in various temperature and atmosphere
                    // conditions means only those with the role should be assigned.  Infantry with
                    // the marine role can also be used.  All other units should be rejected.
                    case XCT:
                        if (mRec.getRoles().contains(XCT)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(MARINE)) {
                            avRating += light_adjust;
                        } else {
                            return null;
                        }
                        break;
                    case FIRE_SUPPORT:
                        if (mRec.getRoles().contains(FIRE_SUPPORT)
                                || mRec.getLongRange() > 0.75) {
                            avRating += medium_adjust;
                        } else if (mRec.getLongRange() > 0.5) {
                            avRating += light_adjust;
                        } else if (mRec.getLongRange() > 0.2) {
                            avRating += min_adjust;
                        } else if (mRec.getRoles().contains(ANTI_AIRCRAFT)
                                || mRec.getRoles().contains(MISSILE_ARTILLERY)
                                || mRec.getRoles().contains(MIXED_ARTILLERY)) {
                            avRating += min_adjust;
                        } else {
                            return null;
                        }
                        break;
                    // Short range fire support requires either the designated role or low
                    // percentage of weapons BV dedicated to long range
                    case SR_FIRE_SUPPORT:
                        if (mRec.getRoles().contains(SR_FIRE_SUPPORT)) {
                            avRating += medium_adjust;
                        } else if (!mRec.getRoles().contains(FIRE_SUPPORT) &&
                                mRec.getLongRange() <= 0.2) {
                            avRating += light_adjust;
                        } else if (mRec.getLongRange() >= 0.5) {
                            return null;
                        }
                        break;
                    case INF_SUPPORT:
                        if (isSpecialized(desiredRoles, mRec)) {
                            return null;
                        } else if (mRec.getRoles().contains(INF_SUPPORT)) {
                            avRating += strictness;
                        } else if (mRec.getRoles().contains(APC)) {
                            avRating += light_adjust;
                        } else if (mRec.getRoles().contains(ANTI_INFANTRY)
                                || mRec.getRoles().contains(URBAN)) {
                            avRating += min_adjust;
                        }
                        break;
                    case OMNI:
                        if (isSpecialized(desiredRoles, mRec) || !mRec.isOmni()) {
                            return null;
                        }
                        break;
                    case MECHANIZED_BA:
                        if (mRec.getUnitType() == UnitType.BATTLE_ARMOR
                                && !mRec.canDoMechanizedBA()) {
                            return null;
                        }
                        break;
                    case MAG_CLAMP:
                        if (mRec.getUnitType() == UnitType.BATTLE_ARMOR
                                && !mRec.hasMagClamp()) {
                            return null;
                        }
                        break;
                    case URBAN:
                        if (isSpecialized(desiredRoles, mRec)) {
                            return null;
                        }else if (mRec.getRoles().contains(URBAN)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(ANTI_INFANTRY) ||
                                mRec.getRoles().contains(SR_FIRE_SUPPORT)) {
                            avRating += light_adjust;
                        } else if (mRec.getRoles().contains(INF_SUPPORT)) {
                            avRating += min_adjust;
                        } else {

                            if (mRec.getRoles().contains(FIRE_SUPPORT) ||
                                    mRec.getRoles().contains(ANTI_AIRCRAFT)) {
                                avRating -= min_adjust;
                            }
                        }
                        if (avRating > 0 && mRec.getUnitType() == UnitType.TANK) {
                            if (mRec.getMechSummary().getUnitSubType().equals("Wheeled")) {
                                avRating += medium_adjust;
                            } else if (mRec.getMechSummary().getUnitSubType().equals("Tracked")) {
                                avRating -= medium_adjust;
                            }
                        }
                        break;
                    case RAIDER:
                        if (isSpecialized(desiredRoles, mRec)) {
                            return null;
                        } else if (mRec.getRoles().contains(RAIDER)) {
                            avRating += medium_adjust;
                        } else {
                            if (mRec.getAmmoRequirement() < 0.2) {
                                avRating += light_adjust;
                            } else if (mRec.getAmmoRequirement() < 0.5) {
                                avRating += min_adjust;
                            }
                            if (mRec.getSpeed() < 3 + medium_adjust - mRec.getWeightClass()) {
                                avRating -= 2 + medium_adjust - mRec.getWeightClass() - mRec.getSpeed();
                            }
                        }
                        break;
                    case INCENDIARY:
                        if (isSpecialized(desiredRoles, mRec)) {
                            return null;
                        } else if (mRec.getRoles().contains(INCENDIARY)) {
                            avRating += medium_adjust;
                        } else {
                            if (mRec.hasIncendiaryWeapon()) {
                                avRating += medium_adjust;
                            } else {
                                avRating -= medium_adjust;
                            }
                        }
                        break;
                    case ANTI_AIRCRAFT:
                        if (isSpecialized(desiredRoles, mRec)) {
                            return null;
                        } else if (mRec.getRoles().contains(ANTI_AIRCRAFT) ||
                                mRec.getFlak() > 0.75) {
                            avRating += medium_adjust;
                        } else if (mRec.getFlak() > 0.5) {
                            avRating += light_adjust;
                        } else if (mRec.getFlak() > 0.2) {
                            avRating += min_adjust;
                        } else {
                            avRating -= light_adjust;
                        }
                        break;
                    case ANTI_INFANTRY:
                        if (mRec.getRoles().contains(ANTI_INFANTRY)) {
                            avRating += medium_adjust;
                        } else if (isSpecialized(desiredRoles, mRec)) {
                            return null;
                        } else if (!mRec.hasAPWeapons()) {
                            avRating -= medium_adjust;
                        }
                        break;
                    // Most military units can be used in a training role, but prioritize those
                    // with the specific role
                    case TRAINING:
                        if (mRec.getRoles().contains(SUPPORT) ||
                                mRec.getRoles().contains(CIVILIAN)) {
                            return null;
                        } else if (mRec.getRoles().contains(TRAINING)) {
                            avRating += strong_adjust;
                        } else {
                            avRating -= light_adjust;
                        }
                        break;

                    case GROUND_SUPPORT:
                        if (mRec.getRoles().contains(GROUND_SUPPORT)) {
                            avRating += medium_adjust;
                        } else {
                            avRating -= medium_adjust;
                        }
                        break;
                    case INTERCEPTOR:
                        if (mRec.getRoles().contains(INTERCEPTOR)) {
                            avRating += medium_adjust;
                        } else {
                            avRating -= medium_adjust;
                        }
                        break;
                    case BOMBER:
                        if (mRec.getRoles().contains(BOMBER)) {
                            avRating += medium_adjust;
                        } else {
                            avRating -= medium_adjust;
                        }
                        break;
                    case ESCORT:
                        if (mRec.getRoles().contains(ESCORT)) {
                            avRating += medium_adjust;
                        } else {
                            avRating -= medium_adjust;
                        }
                        break;
                    case CAVALRY:
                        if (isSpecialized(desiredRoles, mRec)) {
                            return null;
                        }
                        avRating += medium_adjust * (mRec.getSpeed() - (7 - mRec.getWeightClass()));
                        break;
                    // Large craft roles
                    case CIVILIAN:
                        if (!mRec.getRoles().contains(CIVILIAN)) {
                            return null;
                        }
                        break;
                    case CARGO:
                        if (mRec.getRoles().contains(CARGO)) {
                            avRating += medium_adjust;
                        } else {
                            avRating -= medium_adjust;
                        }
                        break;
                    case SUPPORT:
                        if (mRec.getRoles().contains(SUPPORT)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(TUG)) {
                            avRating += light_adjust;
                        } else if (mRec.getRoles().contains(CARGO)
                                    || mRec.getRoles().contains(CIVILIAN)) {
                            avRating += min_adjust;
                        } else {
                            return null;
                        }
                        break;
                    case TUG:
                        if (!mRec.getRoles().contains(TUG)) {
                            return null;
                        }
                        break;
                    case POCKET_WARSHIP:
                        if (mRec.getRoles().contains(POCKET_WARSHIP)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(ASSAULT)) {
                            avRating += min_adjust;
                        } else {
                            return null;
                        }
                        break;
                    case ASSAULT:
                        if (mRec.getRoles().contains(ASSAULT)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(POCKET_WARSHIP)) {
                            avRating += min_adjust;
                        } else {
                            return null;
                        }
                        break;
                    case MECH_CARRIER:
                        if (mRec.getRoles().contains(MECH_CARRIER)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(TROOP_CARRIER)) {
                            avRating += min_adjust;
                        } else {
                            return null;
                        }
                        break;
                    case ASF_CARRIER:
                        if (mRec.getRoles().contains(ASF_CARRIER)) {
                            avRating += medium_adjust;
                        } else {
                            return null;
                        }
                        break;
                    case BA_CARRIER:
                        if (!mRec.getRoles().contains(BA_CARRIER)) {
                            return null;
                        }
                        break;
                    case INFANTRY_CARRIER:
                        if (mRec.getRoles().contains(INFANTRY_CARRIER)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(TROOP_CARRIER)) {
                            avRating += min_adjust;
                        } else {
                            return null;
                        }
                        break;
                    case VEE_CARRIER:
                        if (mRec.getRoles().contains(VEE_CARRIER)) {
                            avRating += medium_adjust;
                        } else if (mRec.getRoles().contains(TROOP_CARRIER)) {
                            avRating += min_adjust;
                        } else {
                            return null;
                        }
                        break;
                    case PROTOMECH_CARRIER:
                        if (!mRec.getRoles().contains(PROTOMECH_CARRIER)) {
                            return null;
                        }
                        break;
                    case TROOP_CARRIER:
                        if (!mRec.getRoles().contains(TROOP_CARRIER)) {
                            return null;
                        }
                        break;
                    case CORVETTE:
                        if (!mRec.getRoles().contains(CORVETTE)) {
                            return null;
                        }
                        break;
                    case DESTROYER:
                        if (!mRec.getRoles().contains(DESTROYER)) {
                            return null;
                        }
                        break;
                    case FRIGATE:
                        if (!mRec.getRoles().contains(FRIGATE)) {
                            return null;
                        }
                        break;
                    case CRUISER:
                        if (!mRec.getRoles().contains(CRUISER)) {
                            return null;
                        }
                        break;
                    case BATTLESHIP:
                        if (!mRec.getRoles().contains(BATTLESHIP)) {
                            return null;
                        }
                        break;
                    default:
                        roleApplied = false;
                }
            }
        }

        // If no roles are required, or a role was requested that was not handled, then revert to
        // generic checking.  This is much simpler, only checking for a few exclusions otherwise
        // using the unmodified availability values.
        if (!roleApplied) {

            // DropShips, JumpShips, and WarShips are excluded from non-combat and civilian role
            // checks
            if (mRec.getUnitType() != UnitType.DROPSHIP &&
                    mRec.getUnitType() != UnitType.JUMPSHIP &&
                    mRec.getUnitType() != UnitType.WARSHIP){

                // Units with the non-combat SUPPORT or CIVILIAN roles should not be used in
                // a general context
                if (mRec.getRoles().contains(SUPPORT) || mRec.getRoles().contains(CIVILIAN)) {
                    return null;
                }

                // Units with only the artillery or missile artillery role should not be used in
                // a general context
                if ((mRec.getRoles().contains(ARTILLERY) ||
                        mRec.getRoles().contains(MISSILE_ARTILLERY)) &&
                        mRec.getRoles().size() == 1) {
                    return null;
                }

            }
        }
        return avRating;
    }

    private static boolean isSpecialized(Collection<MissionRole> desiredRoles,
            ModelRecord mRec) {

        // Only units with role tags can be specialized
        if (mRec.getRoles().isEmpty()) {
            return false;
        }

        // Non-combat SUPPORT role
        if (mRec.getRoles().contains(SUPPORT) && !desiredRoles.contains(SUPPORT)) {
            return true;
        }

        // Non-combat CIVILIAN role
        if (mRec.getRoles().contains(CIVILIAN) && !desiredRoles.contains(CIVILIAN)) {
            return true;
        }

        // The only thing this unit does is provide artillery support.  DropShips are excluded
        // from this check as they naturally provide more than one function.
        if (mRec.getUnitType() != UnitType.DROPSHIP &&
                (mRec.getRoles().size() == 1) &&
                (mRec.getRoles().contains(ARTILLERY) || mRec.getRoles().contains(MISSILE_ARTILLERY))) {

            return !desiredRoles.contains(ARTILLERY) &&
                    !desiredRoles.contains(MISSILE_ARTILLERY) &&
                    !desiredRoles.contains(MIXED_ARTILLERY);

        }

        return false;
    }

    public static MissionRole parseRole(String role) {
        switch (role.toLowerCase().replace("_", " ")) {
            case "recon":
                return RECON;
            case "fire support":
                return FIRE_SUPPORT;
            case "command":
                return COMMAND;
            case "sr fire support":
                return SR_FIRE_SUPPORT;
            case "spotter":
                return SPOTTER;
            case "urban":
                return URBAN;
            case "infantry support":
            case "inf support":
                return INF_SUPPORT;
            case "cavalry":
                return CAVALRY;
            case "raider":
                return RAIDER;
            case "incendiary":
            case "incindiary":
                return INCENDIARY;
            case "ew support":
                return EW_SUPPORT;
            case "artillery":
                return ARTILLERY;
            case "missile artillery":
                return MISSILE_ARTILLERY;
            case "anti aircraft":
                return ANTI_AIRCRAFT;
            case "anti infantry":
                return ANTI_INFANTRY;
            case "apc":
                return APC;
            case "specops":
                return SPECOPS;
            case "cargo":
                return CARGO;
            case "support":
                return SUPPORT;
            case "bomber":
                return BOMBER;
            case "escort":
                return ESCORT;
            case "interceptor":
                return INTERCEPTOR;
            case "ground support":
                return GROUND_SUPPORT;
            case "training":
                return TRAINING;
            case "assault":
                return ASSAULT;
            case "mech carrier":
                return MECH_CARRIER;
            case "asf carrier":
                return ASF_CARRIER;
            case "vee carrier":
                return VEE_CARRIER;
            case "infantry carrier":
                return INFANTRY_CARRIER;
            case "ba carrier":
                return BA_CARRIER;
            case "protomech carrier":
                return PROTOMECH_CARRIER;
            case "tug":
                return TUG;
            case "troop carrier":
                return TROOP_CARRIER;
            case "pocket warship":
                return POCKET_WARSHIP;
            case "corvette":
                return CORVETTE;
            case "destroyer":
                return DESTROYER;
            case "frigate":
                return FRIGATE;
            case "cruiser":
                return CRUISER;
            case "battleship":
                return BATTLESHIP;
            case "engineer":
                return ENGINEER;
            case "marine":
                return MARINE;
            case "mountaineer":
                return MOUNTAINEER;
            case "xct":
                return XCT;
            case "paratrooper":
                return PARATROOPER;
            case "anti mek":
                return ANTI_MEK;
            case "omni":
                return OMNI;
            case "mechanized ba":
                return MECHANIZED_BA;
            case "mag clamp":
                return MAG_CLAMP;
            case "field gun":
                return FIELD_GUN;
            case "civilian":
                return CIVILIAN;
            case "minesweeper":
                return MINESWEEPER;
            case "minelayer":
                return MINELAYER;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
