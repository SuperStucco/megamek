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
package megamek.common.weapons.missiles;

import megamek.common.alphaStrike.AlphaStrikeElement;
import megamek.common.Mounted;
import megamek.common.weapons.CLIATMWeapon;

/**
 * @author Sebastian Brocks, edited by Greg
 */
public class CLIATM12 extends CLIATMWeapon {
    private static final long serialVersionUID = 1L;

    public CLIATM12() {
        super();
        this.name = "Improved ATM 12";
        this.setInternalName("CLiATM12");
        this.addLookupName("Clan iATM-12");
        this.heat = 8;
        this.rackSize = 12;
        this.minimumRange = 4;
        this.shortRange = 5;
        this.mediumRange = 10;
        this.longRange = 15;
        this.extremeRange = 20;
        this.tonnage = 7.0;
        this.criticals = 5;
        this.bv = 333; // Ammo BV is 78
        this.cost = 700000;
        this.shortAV = this.getBaseAeroDamage(); // This is a streak weapon so we use the rack size for the AV
        this.medAV = this.shortAV;
        this.maxRange = RANGE_MED;
        rulesRefs = "65, IO";
        techAdvancement.setTechBase(TechBase.CLAN)
                .setIntroLevel(false)
                .setUnofficial(false)
                .setTechRating(TechRating.F)
                .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.E)
                .setClanAdvancement(3054, 3070, DATE_NONE, DATE_NONE, DATE_NONE)
                .setClanApproximate(true, false, false, false, false)
                .setPrototypeFactions(Faction.CCY)
                .setProductionFactions(Faction.CCY);
    }

    @Override
    public double getBattleForceDamage(int range, Mounted<?> fcs) {
        if (range == AlphaStrikeElement.SHORT_RANGE) {
            return 3.6;
        } else if (range == AlphaStrikeElement.MEDIUM_RANGE) {
            return 2.4;
        } else {
            return 1.2;
        }
    }
}
