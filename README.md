![alt-text](src/main/resources/assets/resewn_pockets/resewn_pockets_lol.png)  
<small>Mod Icon for Resewn Pockets, made in MS Paint in a whopping thirty seconds of hard work  

Resewn Pockets
===

Resewn Pockets is a Fabric mod requested by a user on Discord and made by GauntRecluse. It is currently in early development.  


## What is this?  
This mod's main feature and focus is creating a serverside system which can add custom conditions for items picking up
items and taking items from chests and other containers or inventories.  
This allows server owners/admins or modpack creators and the like to more precisely manage limitations on
a player's inventory space on Fabric servers or modpacks with some level of pre-chewed work.  
It is currently only intended for Fabric 1.21.1, but may be ported to other versions in the future.

## How does it work?  
The mod uses Mixins, a set of tools to modify code external to your mode during the game launching and loading (oversimplifying),
to edit the code that checks for whether a player can pick up/hold an item or not, and adds a check for this mod's logic.  
Then, it uses a configuration system and allows other mods to modify it via Mixins to allow to feed custom logic to those checks. In short:  
Config + External Mixins -Custom Logic-> ResewnPockets -> Mixins -> Minecraft Vanilla checks  


## Where documentation? Where downloads? How config?  
Currently, the config system is in development, and so is the rest of the mod. It is, as such, unavailable for download or installation.  
As such, the following are the _planned_ installation methods, but are _subject_ to change.  

### Compatibility notice!!  
Resewn Pockets tries to be as compatible as it can with other mods, trying to avoid overwriting vanilla behavior or changing it
so much that incompatibilities arise. However, as with any mod that uses Mixins, there is a risk of incompatibility.  
If you encounter a compatibility error with an otherwise proper set-up, it is recommended to try and use mods such as
[Mixin Conflict Helper](https://modrinth.com/mod/mixin-conflict-helper) to figure out if there is a conflicting Mixin and with which mod.  
If you believe it to be a conflict that I could fix, or you want my opinion on it, open an issue on this mod before you
make one with the conflicting mod.

### Installation and setup:  
This mod will not do anything on its own (other than add checks that'll nibble on your performance), Resewn Pockets uses 
FzzyConfig for its configuration, meaning the settings are global across all your worlds. No per-world overrides are being worked on, 
but it is not entirely out of the picture.  
Editing the config is done via the TOML file generated through FzzyConfig directly, Resewn Pockets is a server-sided mod so it does not make use of FzzyConfig's GUI configuration.  
The guide to configuring the mod for the supported config logic can be found in the Wiki(To be released when the mod gets to a releaseable state).  

### If the config isn't enough:  
Resewn Pockets intends to not close itself off from another mod's Mixins being used to change its logic or inject its own.  
As such you may, at your own risk, use Mixins to further manually customize logic to tailor your needs.

### If the config and mixins aren't enough:  
This mod's release is to be licensed under the LGPL v2.1 license, as such you are free to make your own fork of it as long as you properly credit this mod, and also license it under the LGPL license.  
You may also use this mod's architecture or mimic its Mixins as part of your own mods, and given that it 
is only a portion of your own project rather than a fork/redistribution/modification of Resewn Pockets, 
you may license such projects freely with proper attributions.