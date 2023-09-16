# FastMinecarts Plugin
[![GitHub Release](https://img.shields.io/github/v/release/certainly1182/FastMinecarts?include_prereleases)](https://github.com/certainly1182/FastMinecarts/releases)
<!--- [![Modrinth Downloads](https://img.shields.io/modrinth/dt/######)](https://modrinth.com/plugin/######) -->

A simple plugin for Minecraft [Spigot](https://spigotmc.org) servers that allows you to change the speed of minecarts depending on the block beneath the rails or globally.

## Features
- Configure the global speed.
- Configure which blocks affect speed.
- Choose the minecart speed for each block.
- Slow minecarts back to vanilla speed when the player disembarks.
## Installation
To install FastMinecarts, follow these steps:
1. Download the plugin JAR file from<!--- [Modrinth](https://modrinth.com/plugin/#####)  or the--> [Releases](https://github.com/certainly1182/FastMinecarts/releases) page.
2. Place the JAR file in the plugins folder of your Spigot (or Spigot fork) server.
3. Start the server and verify that the plugin loaded successfully.
## Configuration
The plugin can be configured via the `config.yml` file in `plugins/FastMinecarts`.

The following configuration options are available:

- `blocks` - List of blocks and their corresponding modified speed.
### Default Config:
```yml
# List of blocks and their corresponding maximum minecart speeds
# Blocks must be from https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
# The default minecart speed is 0.4
default-speed: 2.0
blocks:
  COARSE_DIRT: 0.4
  SOUL_SAND: 0.2
```
## Usage
The FastMinecarts plugin has the command "/FastMinecarts Reload" to reload the config, and it requires the permission: "FastMinecarts.reload" but also can be run by an op. Simply install and configure the plugin on your server to start using it.

The global speed gets applied when a player enters a minecart on rails, and when you ride the minecart above the configured blocks it will change the speed to the configured value.
