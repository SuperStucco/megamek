# MegaMek—TableTop BattleTech on your computer

## Table of Contents

1. [About](#about)
2. [Status](#status)
3. [Running MegaMek](#running-megamek)
    1. [Installing Or Updating Your Java Runtime](#installing-or-updating-your-java-runtime)
        1. [Adoptium (Windows)](#adoptium-windows)
        2. [Adoptium (Mac)](#adoptium-mac)
        3. [Linux](#linux)
4. [Connecting](#connecting)
5. [Playing The Game](#playing-the-game)
    1. [Pre-game Lobby](#pre-game-lobby)
    2. [Initiative Report](#initiative-report)
    3. [Movement Phase](#movement-phase)
    4. [Movement Report](#movement-report)
    5. [Weapons Fire Phase](#weapons-fire-phase)
    6. [Weapons Fire Report](#weapons-fire-report)
    7. [Physical Attacks Phase](#physical-attacks-phase)
    8. [End of Turn Report](#end-of-turn-report)
6. [Custom Units](#custom-units)
7. [Advanced Map Settings](#advanced-map-settings)
8. [Differences Between The Board Game and MegaMek](#differences-between-the-board-game-and-megamek)
9. [Compiling](#compiling)
10. [Support](#support)
11. [Contact & Further Information](#contact--further-information)
12. [Licensing](#licensing)

## About

MegaMek is a Java version of BattleTech that you can play with your friends over the internet. All rules from Total
Warfare are implemented. Rules from Tactical Operations and Strategic Operations are constantly in the works, and many
of those rules and weapons are already implemented.

Players can create their own units, maps, and scenarios for use with MegaMek. MegaMek supports all unit types, from
infantry, BattleMek, and vehicles, to Aerospace fighters, DropShips, and warships. Ground battles as well as space
battles can be played.

If you would like information about how to play the game, see the "PLAYING THE GAME" section, below. If you're having
trouble getting MegaMek started, see the next section, "RUNNING MEGAMEK."

For complete game rules, consult the Classic BattleTech rule books published by Catalyst Game Labs. These books
include [Total Warfare](https://store.catalystgamelabs.com/collections/battletech/products/battletech-total-warfare-pdf),
[Tactical Operations: Advanced Rules](https://store.catalystgamelabs.com/collections/battletech/products/battletech-tactical-operations-advanced-rules),
[Tactical Operations: Advanced Units & Equipment](https://store.catalystgamelabs.com/collections/battletech/products/battletech-tactical-operations-advanced-units-equipement),
and [Strategic Operations](https://store.catalystgamelabs.com/collections/battletech/products/battletech-strategic-operations).

## Status

| Type           | MM Status                                                                                                                                                              | MML Status                                                                                                                                                                       | MHQ Status                                                                                                                                                        |
|----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Latest Release | [![Release](https://img.shields.io/github/release/MegaMek/megamek.svg)](https://gitHub.com/MegaMek/megamek/releases/)                                                  | [![Release](https://img.shields.io/github/release/MegaMek/megameklab.svg)](https://gitHub.com/MegaMek/megameklab/releases/)                                                      | [![Release](https://img.shields.io/github/release/MegaMek/mekhq.svg)](https://gitHub.com/MegaMek/mekhq/releases/)                                                 |
| Javadocs | [![javadoc](https://badgen.net/badge/javadoc/master/red?icon=github)](https://megamek.org/megamek) | [![javadoc](https://badgen.net/badge/javadoc/master/red?icon=github)](https://megamek.org/megameklab) | [![javadoc](https://badgen.net/badge/javadoc/master/red?icon=github)](https://megamek.org/mekhq) |
| License        | [![GPLv3 license](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0.html)                                                     | [![GPLv3 license](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0.html)                                                               | [![GPLv3 license](https://img.shields.io/badge/License-GPLv3-blue.svg)](http://www.gnu.org/licenses/gpl-3.0.html)                                                 |
| Build (CI)     | [![MM Nightly CI](https://github.com/MegaMek/megamek/workflows/MegaMek%20Nightly%20CI/badge.svg)](https://github.com/MegaMek/megamek/actions/workflows/nightly-ci.yml) | [![MML Nightly CI](https://github.com/MegaMek/megameklab/workflows/MegaMekLab%20Nightly%20CI/badge.svg)](https://github.com/MegaMek/megameklab/actions/workflows/nightly-ci.yml) | [![MHQ Nightly CI](https://github.com/MegaMek/mekhq/workflows/MekHQ%20Nightly%20CI/badge.svg)](https://github.com/MegaMek/mekhq/actions/workflows/nightly-ci.yml) |
| Issues         | [![GitHub Issues](https://badgen.net/github/open-issues/MegaMek/megamek)](https://gitHub.com/MegaMek/megamek/issues/)                                                  | [![GitHub Issues](https://badgen.net/github/open-issues/MegaMek/megameklab)](https://gitHub.com/MegaMek/megameklab/issues/)                                                      | [![GitHub Issues](https://badgen.net/github/open-issues/MegaMek/mekhq)](https://gitHub.com/MegaMek/mekhq/issues/)                                                 |
| PRs            | [![GitHub Open Pull Requests](https://badgen.net/github/open-prs/MegaMek/megamek)](https://gitHub.com/MegaMek/megamek/pull/)                                           | [![GitHub Open Pull Requests](https://badgen.net/github/open-prs/MegaMek/megameklab)](https://gitHub.com/MegaMek/megameklab/pull/)                                               | [![GitHub Open Pull Requests](https://badgen.net/github/open-prs/MegaMek/mekhq)](https://gitHub.com/MegaMek/mekhq/pull/)                                          |
| Code Coverage  | [![MegaMek codecov.io](https://codecov.io/github/MegaMek/megamek/coverage.svg)](https://codecov.io/github/MegaMek/megamek)                                             | [![MegaMekLab codecov.io](https://codecov.io/github/MegaMek/megameklab/coverage.svg)](https://codecov.io/github/MegaMek/megameklab)                                              | [![MekHQ codecov.io](https://codecov.io/github/MegaMek/mekhq/coverage.svg)](https://codecov.io/github/MegaMek/mekhq)                                              |

Note that not everything has been implemented across the suite at this time, which will lead to gaps.

## RUNNING MEGAMEK

Java programs run in their own environment, called a Virtual Machine or VM for short. These Java VMs are available on
most systems from a variety of sources.

Windows users: To start MegaMek, run the MegaMek.exe file. If this fails to start MegaMek, see the "INSTALLING OR
UPDATING YOUR JAVA RUNTIME" section, below.

Other graphical OSes: Many other graphical OSes, such as macOS, will allow you to double-click the .jar file to run it.
If this does not work, try running MegaMek from the command line.

Running MegaMek from the command line: To do this using Sun Java, or most other implementations, navigate to the
directory containing the .jar file and run: `java -jar MegaMek.jar`.

If none of the above options work for you, see the "INSTALLING OR UPDATING YOUR JAVA RUNTIME" section, below.

### INSTALLING OR UPDATING YOUR JAVA RUNTIME

Of the versions available, we now require Java 17 LTS as the bare minimal version. Newer versions should work but are
not currently supported.

#### Adoptium (Windows)

For Windows, follow the instructions [here](https://github.com/MegaMek/megamek/wiki/Updating-to-Adoptium) to ensure Java
is installed correctly for the most seamless experience.

#### Adoptium (Mac)

For Mac, download the installer
from [Adoptium]( https://adoptium.net/temurin/releases/?os=mac&version=17&arch=aarch64&package=jre) directly for your
version of macOS and underling platform (AARCH64 is for M-Series Mac's).

#### Linux

For Linux, your distribution should have a version of Java available via your package manager.

## CONNECTING

MegaMek is a network game. One player hosts a game and the rest of the players connect. The connecting players can
connect from anywhere with a TCP/IP connection to the host, including the same computer (see "hot seat," below.)

To host a game, press the "Host a New Game..." button in the main menu. Fill in your name and click "Okay." The password
field allows you to specify a password for certain server commands (Type /help in the chat line to get a list of server
commands.) If you don't specify a password, anybody is allowed to reset the server or kick players, so be careful.

Once the lobby screen comes up, other players can connect and the game can begin. The other players will need to know
the IP address of the host computer. There are several ways to determine your IP address. If you are on Windows 9x/ME,
you can use the Run command on the start menu and enter `winipcfg`. On Windows 2000/NT/XP/Vista/7/8/10, you will need to
open a command prompt and type `ipconfig`. As a last resort, there are some web pages, such
as [WhatIsMyIP](https://www.whatismyip.com) that will try to detect your IP address, but if your internet service uses a
proxy or firewall, these pages cannot accurately detect it.

To connect to a game, press the `Connect To A Server` button on the main menu and fill in your player name and the
host's IP address.

To play in a hot seat fashion, you can run the host and any number of other players on the same computer. First, launch
and host a game. Then, launch another copy of the game for each connecting player, and in the "Server Address" field of
the `Connect To A Server` dialog, type `localhost`. Each player will need to switch to his/her copy of the game to play
their turn.

To start a dedicated server, use the `-dedicated` command line switch. A dedicated server just runs the server, in the
console, without a "host" player in the game. People, including yourself, can join it like a game with a host. The
dedicated server reads the password and port options from the `clientsettings.xml` file, or uses the default (no
password and port 2346.) If you want the dedicated server to load a saved game, supply the filename at the end of
`-dedicated` args, like this: `-dedicated savedgame.sav` or `-dedicated -port 2346 savedgame.sav`. To stop the server,
you will need to tell the Java machine to halt execution, which is `CTRL + C` on most platforms.

## PLAYING THE GAME

### Pre-game Lobby

Here you can chat, specify what units you wish to use, select map settings, declare starting positions, and change your
player's color. When everybody has at least one unit, you may all hit "ready" to start the game. Most changes to the
game parameters will cancel your ready status, so you should wait for everybody to finish choosing their units and
positions. Note: If you wish to use custom unit designs, please see the section below entitled "CUSTOM UNITS."

You can enter the map selector by clicking on the "Select Map" tab at the top of the lobby. You can change the
dimensions of the map boards (in hexes) or the dimensions of the whole map (in boards). All maps are loaded off the
server. Most of the map boards that MegaMek comes with are 16x17.

Below the map size inputs is a not-to-scale representation of how the boards are laid out relative to each other. The
middle column lists the current maps, and the rightmost column lists the available maps. To change the current map,
select it in the middle column, select the map you want in the right column, and press the "<<" button between the two
columns. You may also select a map by clicking on its number in the map layout grid. When you select a map, (other
than [SURPRISE] or [RANDOM]), you have the choice or rotating it 180 degrees (North becomes South, East becomes West,
and vice versa) by clicking on the "Rotate Board" checkbox; the fact that the map is rotated will be shown in the middle
column.

- **[SURPRISE]** means that the Server will pick a random map and not tell you what it has picked until the game starts.
- **[RANDOM]** means that the Server will pick a map as soon as you hit "Okay."
- **[GENERATED]** means that the server will create random terrain for you to play on. You need to use the "generated
  map settings" button to select what kind of terrain you want.

There is a set of settings with a drop-down list of choices. For most settings, you can pick "none," "low," "medium"
or "high." Except for cities, where you pick a type of city instead.

- **Theme**: leave blank for the default theme, or enter a theme supported by your tileset. grass, jungle, lunar, Mars,
  snow, and volcano are supported by the standard tileset
- **Elevation**: how hilly you want the map
- **Cliffs**: chance of 2+ level elevation changes if you have enough elevation already
- **Woods**: higher settings have more and larger tree patches and more heavy woods
- **Roughs**: higher settings have more and larger rough patches
- **Lakes**: works the same as woods, but deep water instead of heavy woods Swamps/Pavement/Ice/Rubble/Fortified all
  work like roughs. Fortified hexes are described in the MaxTech rulebook.
- **River**: chance to have a river running across the map
- **Road**: chance to have a single road running across the map
- **Craters**: chance for craters, also the size and number. Good for a "moonscape" map
- **City:**
  - **HUB**: roads wander out from the center with plenty of twists and turns. A common pattern for older european
      cities.
  - **GRID**: vertical and horizontal roads divide the city into rectangular blocks. A pattern common in newer cities,
      especially in North America and asia.
  - **METRO**: a grid with roads forming a diagonal X from the center as well.

Water and swamp will affect the city plan, as the builders will have to make bridges to cross it or just give up and
stop the road at the edge. Mountain tops (4+ height) are expensive to build on, so they will only build in the valleys.
Other terrain will just be bulldozed in the name of progress, though you may find a few hexes left between buildings.

There is also an "advanced" button, which lets you fine-tune the map to your liking. See the "advanced map settings"
section near the end of this file

### Initiative Report

Each player's initiative rolls, and the corresponding turn order will be shown here.

### Movement Phase

The buttons at the bottom of the screen let you change between different modes of movement, switch to another unit, or
commit to your current path. You do not have to move the first selected unit first.

Left-click on the map to specify a hex to move to. A path should appear on the board, showing your unit's path to the
target hex. The numbers in the center represent how many movement points you will have to use to reach each hex. Cyan
indicates walking, yellow running, and red jump movement. Dark gray sections of the path indicate where you have
exceeded your movement capacity or other illegal moves. You can drag the mouse to see movement options for several
different hexes. Please note that you can change the colors used to display the movement points by editing
`clientsettings.xml`; you may specify a color in RGB notation (e.g., 255 255 255 for white) or with any of the following
text values: black, blue, cyan, darkgray, gray, green, lightgray, magenta, orange, pink, red, white, or yellow.

Units can also be moved by using "waypoints." By clicking each hex between the unit's current location and destination,
you can delineate the exact path to be taken.

Holding the `Shift` key while clicking on the map allows you to change the unit's facing without moving. Since you
automatically change facing while moving, this is best done at the end of any movement.

To move backwards, click the "Back Up" button before clicking a hex in the arc behind the unit. To jump, click the
"Jump" button before the destination hex. Facing changes during jumps is free.

Prone units can change facing without getting up at the normal cost. To get up, click the "Get Up" button, then the
desired destination hex and/or facing. Facing changes performed immediately after getting up are free. To get up and
back up, click "Get Up," then "Back Up," then the destination hex and/or facing. You cannot get up and jump in the same
turn.

Charging and death from the above attacks are also declared during the movement phase. Click the "Charge" or "D.F.A."
button and then click on the mek you wish to target. If the attack is valid, it will be sent to the server immediately
(but resolved during the physical attack phase.) If the program is not using the path you want for your attack, you may
plot a path near the target using the appropriate type of movement and then use the charge or dfa button to complete the
attack.

If a vehicle or Mek that has sufficient empty space starts a turn in the same hex as a friendly infantry unit, it can
Load the unit as its first move of the turn. A vehicle or Mek that is already carrying an infantry unit can unload the
unit as its last movement of the turn.

If an Anti-Mek infantry platoon or Battle Armor squad has succeeded in mounting a Mek with a Swarm attack, the Mek can
attempt to dislodge the squad during the movement phase in one of three ways:

1) jumping (requires a Pilot Skill Roll, with a +4 modifier),
2) walking into (and perhaps through) a Level 2 or deeper water hex, or
3) ending its turn in a hex that is on fire (this will not work against Battle Armor squads).

Swarming infantry automatically die if they are dislodged into a Level 1 or deeper water hex. Infantry dislodged in the
Movement Phase cannot move or fire for the rest of the turn. Swarming units cannot move off of their target Mek until
they use the Stop Swarm action in the Weapons Fire Phase.

VTOLs and VTOL BA use the "go up" and "go down" buttons to change elevation (flight level).

Movement around structures (buildings/bridges) is a little more complicated because of the different floors a unit can
occupy. The "climb mode" button sets your current preference. "Go through" means you prefer to go under bridges or
through the walls of buildings if there is a choice. "Climb up" means you prefer to climb on top of structures when
possible. You can change mode multiple times during your movement. For example, a StormCrow Prime could use "climb up"
to go over a heavy building to save MP, then change to "go through" to pass a light building that won't support its
weight.

Infantry can move up and down inside buildings using the stairs. This is achieved by using the "go up" and "go down"
buttons. Also, infantry that jumps into a building can use the "go up" and "go down" buttons to choose which floor to
jump through the window of. Click the building hex you want to jump into first, and the game will pick the highest floor
you can reach (or the roof). Then use the "go down" button to select a lower floor if you want to.

The `Esc` key clears all current movement.

### Movement Report

If any units needed piloting skill rolls during their movement, a report showing the results of these rolls will be
shown.

### Weapons Fire Phase

If you need to check the range and line of sight (LoS) between two hexes, there are two tools available for you to use;
the LoS tool and the ruler tool. To use the LoS tool, hold `CTRL` and click on the two hexes you want to check. The
ruler tool works like the LoS tool, but uses the `ALT` key instead of the `CTRL` key. The ruler tool can also be used by
middle-clicking the two hexes with a three-button mouse. Both tools will pop up a window that tells you what terrain is
intervening and whether one or both hexes has partial cover. The ruler tool also draws the line of sight on the board so
that you can see which hexes LoS passes through.

You can switch between 'Mek line of sight and non-'Mek line of sight in the "LoS Setting" panel, located under the
"View" menu at the top of the screen. 'Mek line of sight and non-'Mek line of sight differ in that 'Meks are assumed to
be "looking" from one elevation level above that of the hex they are in, whereas infantry and vehicles only take up the
elevation level of their hex.

The buttons at the bottom of the screen allow you to fire your weapons, switch to another unit, or commit to your
currently declared fire. Again, you do not need to declare fire for your first selected unit first.

To target another unit, click on it on the board. Targeting information for your current weapon should appear in the mek
display window. To fire your current weapon at the target, press the "Fire" button at the bottom of the screen. To
switch to another weapon, click on its name at the top of the mek display window. Spread your fire among multiple
targets by repeating these steps using unassigned weapons. When all desired weapons have been assigned to a target,
press the "Done" button at the bottom of the screen.

Note that fire is resolved in the order that it is declared, so if you feel that it is a good idea to fire weapons in a
different order than they are listed, go ahead.

Secondary facing changes (torso twists) are achieved by holding `Shift` and clicking on the board. Your unit will
attempt to change its secondary facing in the direction specified. If you assign a weapon to a target and then attempt a
facing change, the weapons fire will be canceled.

You cannot switch to another unit after declaring some weapons fire. To switch to another unit, first cancel all current
fire by hitting the `Esc` key. When you hit the fire button for your last available weapon, all declared fires will be
committed.

An Anti-Mek trained infantry platoon or Battle Armor squad in the same hex as a Mek can conduct a Leg Attack or a Swarm
Mek attack. The base-to-hit number varies with the number of men in the unit. A Swarm Mek attack against a Mek that is
prone and/or immobile gains a -4 modifier to its to-hit roll. A successful Leg Attack may cause critical damage to one
of the Meks legs; if no critical is rolled, the leg will take 4 points of damage (which may damage the leg's internal
structure and cause another critical roll). A successful Swarm Mek attack does no damage in the turn it hits, but it
means that the unit has attached itself to the 'Mek, and can begin to cause significant damage on later turns. Starting
the turn after the unit starts a Swarming a 'Mek, all attacks by that unit automatically hit, use a location chart that
is more dangerous than normally, and automatically roll for a Thru-Armor Critical. A unit Swarming a Mek can also choose
to Stop Swarming in any weapons phase. The Leg Attack, Swarm Mek, and Stop Swarm actions are all "solo" actions: the
unit can take no other action in the Weapons Fire Phase. Attempts to do so will cancel the "solo" action. Swarming
infantry cannot be targeted by any attacks other than a "Brush Off" attack (see Physical Attacks Phase).

### Weapons Fire Report

If there were any weapon attacks, the server will resolve them all at the end of the phase in the order they were
declared. The results will be shown in a report.

### Physical Attacks Phase

To declare a physical attack, select your target on the board and click the button corresponding to the attack you want
to make. If a physical attack type is unavailable (or isn't programmed yet), the button will be greyed out.

Units ineligible to make physical attacks due to being out of range, having made weapons attacks, or for any other
reason, will be skipped. If all units are ineligible, the entire phase will be skipped.

Prone Meks that are in the same hex as a vehicle can punch the vehicle if they have both arms. Prone Meks that are in a
Clear or Pavement hex as an infantry platoon or Battle Armor squad can "thrash" at the unit if they have at least one
arm or leg. Be warned that "thrashing" causes a Pilot Skill Roll that, if failed, causes the Mek to suffer damage as if
it has fallen 1 level.

Meks that are not prone that have been Swarmed by an infantry platoon or Battle Armor squad can attempt to "Brush Off"
the squad with either or both arms. Any "Brush Off" attack that fails to hit the infantry will inflict punch damage on
the Mek. Any successful "Brush Off" attack ends a Swarm Mek attack.

### End of Turn Report

If there were any physical attacks, the results will be shown. The results of the Heat and End phases will be shown.

After this phase, it's time for initiative again! Hurrah!

## CUSTOM UNITS

All units (meks, vehicles, infantry, etc.) are located in the data/mekfiles directory. They may be individual files or
zipped up into archives (".zip"), and you may also create subdirectories if you like.

We recommend creating a folder called Customs in the data/mekfiles directory. Then using this folder to store all custom
units.

As of 0.49.13, We've removed the unsupported and unofficial folders. Over the years the unsupported units dropped to
only a couple. The unofficial folder is available
from [the Extras repository](https://github.com/MegaMek/megamek-extras).

Over the years the number of custom mek builders that support MegaMek has shrunk. For the best compatibility we
recommend using MegaMekLab. Any issues from the use of other programs should be directed to their developers for
support.

### Note of file types

MegaMek uses two file types for units. Files with the extension MTF are meks, and all other unit types are BLK files.
Both are editable with a quality text editor, but we recommend not hand editing files as it can break the programs.

## ADVANCED MAP SETTINGS

_**Tip:** Use the basic settings, then select advanced, and the boxes will be filled in with the values from the basic
settings._

- **Board Size**: set the size of the map sheet generated. When using generated maps, it is better to generate one
  large play area than to try and combine multiple map sheets.
- **Theme**: leave blank or enter a theme supported by the tileset. Grass, Lunar, Mars, Snow are themes supported by
  the standard tileset.
- **Elevation settings:**
  - **Amount of elevation**: changes the "roughness" of the map, a low number will have fewer elevation changes than a
      high number Elevation range: height difference between the lowest and highest hex on the map. Level 0 is set to
      the most common height.
  - **Probability of inverting**: % chance to make a sinkhole instead of a mountain.
  - **Algorithm**: 0 generates rolling hills, 1 generates spiky terrain, 2 combines both generators. 0 is likely to
      have less LOS blocking terrain.
  - **Cliffs**: % chance to change a steep slope into a cliff. For example, if a group of level 1 hexes have level 0
      and level 2 hexes adjacent, they will all be moved to level 0, giving a cliff for meks to hide behind. The effect
      is quite subtle unless you have quite a high-elevation range to begin with.
- **Patch terrain settings (woods, roughs, swamps, lakes, pavements, rubble, fortified, ice)**: Each of these works the
  same way, but places a different type of terrain.
  - **Number of XXX**: the number of patches which would be present on a 16x17 map. scales up if you have a bigger
      map.
  - **XXX size in hexes**: the size of each patch of terrain
  - **Probability for heavy woods/deep water**: % chance to place instead of light.
  - **River/road settings**: Probability is a % chance to have one on the map. If a road crosses water, you'll get a
      bridge.
- **Crater settings:**
  - **Probability for craters**: % chance that craters are present Number of craters: as for the number of woods, the
      number that would be present on a 16x17 map, scaled up for larger maps.
  - **Crater radius**: size range for each crater.
- **Special effects settings**: Each probability is a % chance. FX modifier changes the amount of effect each one has.
  Values should be small, e.g. -3 to +3
  - **Fires**: woods hexes will be set on fire or already burned down to rough at the start of the game. + modifier
      increases number of burned down hexes, —modifier increases number of unscathed woods.
  - **Frozen water**: water hexes are ice-covered. +modifier decreases the water depth, so for example with a
      modifier of 1, shallow water hexes are frozen solid, and deep water hexes are converted to ice-covered shallow
      water.
  - **Flooded map**: hexes with negative elevations are converted to water hexes, while level 0 hexes are converted
      to swamps. Ideal for hovercraft and naval units. modifier changes the "sea level"
  - **Drought**: water hexes are dried up, to shallower water, swamp or rough. Modifier changes the severity of the
      drought.
  - **Special effects can be combined**: e.g., flood + drought = rocky shore, flood + frozen + high modifier = glacier
- **City Settings**: City type is the same as for the basic settings.
  - **City Blocks (0-) [16 default]**: higher numbers mean more roads on the map. Scales with map size.
  - **CF min/max (1–150) [10–100 default]**: CF range for generated buildings. The CF determines the building type
      from the table in BMR.
  - **Floors min/max (1-50) [1-6 default]**: height range for generated buildings
  - **Density (1–100) [75 default]**: % chance of a building in each hex where a building is possible. Also chance to
      try and build multi-hex buildings.

## DIFFERENCES BETWEEN THE BOARD GAME AND MEGAMEK

Although MegaMek tries to be faithful to the original board game rules, in some cases, due to technical or design
limitations, this is not possible. These differences are not considered "bugs." If you spot any more discrepancies,
please contact the author (see "CONTACT" below.)

- If the line of fire lies along the edge of two hexes, in the board game, the defender chooses which hex to use.
  Instead, MegaMek chooses the hex that most favors the defender.
- When punching, you automatically punch with both arms, if possible. This means you cannot punch two different targets
  in the same round.
- When kicking, you automatically use the leg with the better chance to hit.
- There are several situations, notably death from above, where a unit is displaced out of a hex, and that unit's owner
  may pick the hex to move to. MegaMek currently picks the hex for you, choosing high elevations over low ones to avoid
  falling damage.

## Compiling

1) Install [Gradle](https://gradle.org/).

2) Follow the [instructions on the wiki](https://github.com/MegaMek/megamek/wiki/Working-With-Gradle) for using Gradle.

### Style Guide

When contributing to this project, please enable the EditorConfig option within your IDE to ensure some basic compliance
with our [style guide](https://github.com/MegaMek/megamek/wiki/MegaMek-Coding-Style-Guide) which includes some defaults
for line length, tabs vs. spaces, etc. When all else fails, we follow
the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

## Support

For bugs, crashes, or other issues you can fill out a [GitHub issue request](https://github.com/MegaMek/MegaMek/issues).

## CONTACT & FURTHER INFORMATION

For more information, and to get the latest version of MegaMek, visit the [website](https://megamek.org).

For more information about the BattleTech board game, visit its [website](https://www.battletech.com).

To submit a bug report, suggestion, or feature request, please visit
our [issue tracker](https://github.com/MegaMek/megamek/issues)

To discuss all things MegaMek, please visit our [Discord](https://discord.gg/megamek)

## Licensing

MegaMek is licensed under a dual-licensing approach:

### Code License

All source code is licensed under the GNU General Public License v3.0 (GPLv3).
See the [LICENSE.code](LICENSE.code) file for details.

### Data/Assets License

Game data, artwork, and other non-code assets are licensed under the Creative Commons Attribution-NonCommercial 4.0
International License (CC-BY-NC-4.0).
See the [LICENSE.assets](LICENSE.assets) file for details.

### BattleTech IP Notice

MechWarrior, BattleMech, `Mech, and AeroTech are registered trademarks of The Topps Company, Inc. All Rights Reserved.
Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of InMediaRes Productions, LLC.

The BattleTech name for electronic games is a trademark of Microsoft Corporation.

MegaMek is an unofficial, fan-created digital adaptation and is not affiliated with, endorsed by, or licensed by
Microsoft Corporation, The Topps Company, Inc., or Catalyst Game Labs.

### Full Licensing Details

For complete information about licensing, including specific directories and files, please see
the [LICENSE](LICENSE) document.
