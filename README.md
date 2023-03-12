# FastMinecarts Plugin
[![GitHub Release](https://img.shields.io/github/v/release/certainly1182/FastMinecarts?include_prereleases)](https://github.com/certainly1182/FastMinecarts/releases)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/######)](https://modrinth.com/plugin/######)

Simple plugin for Minecraft [Paper](https://papermc.io) servers that changes the speed of minecarts depending on the block beneath the rails.

## Features
- Configure which blocks affect speed.
- Choose the minecart speed for each block.
- Slow minecarts back to vanilla speed when player disembarks.
## Installation
To install FastMinecarts, follow these steps:
1. Download the plugin JAR file from [Modrinth](https://modrinth.com/plugin/#####) or the [Releases](https://github.com/certainly1182/FastMinecarts/releases) page.
2. Place the JAR file in the plugins folder of your Paper (or Paper fork) server.
3. Start the server and verify that the plugin loaded successfully.
## Configuration
The plugin can be configured via the `config.yml` file located in `plugins/FastMinecarts`.

The following configuration options are available:

- `blocks` - List of blocks and their corresponding modified speed.
### Default Config:
```yml
# List of blocks and their corresponding maximum minecart speeds
# Blocks must be from https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
# The default minecart speed is 0.4
blocks:
  GRAVEL: 0.8
  SOUL_SAND: 0.2
```
## Usage
The FastMinecarts plugin does not *yet* have any commands or permissions. Simply install and configue the plugin on your server to start using it.

When a player enters a minecart on rails above configuered blocks, the speed is modified.
