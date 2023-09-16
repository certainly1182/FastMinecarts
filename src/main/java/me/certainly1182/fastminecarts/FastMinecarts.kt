package me.certainly1182.fastminecarts

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Minecart
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.vehicle.VehicleExitEvent
import org.bukkit.event.vehicle.VehicleMoveEvent
import org.bukkit.plugin.java.JavaPlugin

class FastMinecarts : JavaPlugin(), Listener, CommandExecutor, TabCompleter {
    private var VANILLA_MAX_SPEED = 0.4
    private var _blockMaxSpeeds = mutableMapOf<Material, Double>()
    private val railTypes = listOf(
            Material.RAIL, Material.POWERED_RAIL,
            Material.DETECTOR_RAIL, Material.ACTIVATOR_RAIL
    )
    override fun onEnable() {
        saveDefaultConfig()
        loadConfig()
        Bukkit.getPluginManager().registerEvents(this, this)
        getCommand("FastMinecarts")?.setExecutor(this)
        getCommand("FastMinecarts")?.tabCompleter = this
    }
    private fun loadConfig() {
        VANILLA_MAX_SPEED = config.getDouble("default-speed", 0.4)
        val blockConfig = config.getConfigurationSection("blocks") ?: return
        _blockMaxSpeeds.clear()
        for (key in blockConfig.getKeys(false)) {
            val material = Material.getMaterial(key)
            if (material != null) {
                _blockMaxSpeeds[material] = blockConfig.getDouble(key)
            }
        }
    }
    @EventHandler(ignoreCancelled = true)
    fun onVehicleMove(event: VehicleMoveEvent) {
        if (event.vehicle !is Minecart) return

        val minecart = event.vehicle as Minecart
        if (minecart.isEmpty) return
        if (minecart.passengers.first() !is Player) return

        val railBlock = event.vehicle.location.block
        if (railBlock.type !in railTypes) return

        val blockBelow = railBlock.getRelative(0, -1, 0)
        val blockMultiplier = _blockMaxSpeeds[blockBelow.type] ?: VANILLA_MAX_SPEED
        minecart.maxSpeed = blockMultiplier
    }

    @EventHandler(ignoreCancelled = true)
    fun onVehicleExit(event: VehicleExitEvent) {
        if (event.vehicle !is Minecart) return
        if (event.exited !is Player) return

        val minecart = event.vehicle as Minecart
        if (minecart.maxSpeed > VANILLA_MAX_SPEED) {
            minecart.maxSpeed = VANILLA_MAX_SPEED
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (command.name.equals("FastMinecarts", ignoreCase = true)) {
            if (sender.hasPermission("FastMinecarts.reload")) {
                if (args.isNotEmpty() && args[0].equals("reload", ignoreCase = true)) {
                    reloadConfig()
                    loadConfig()
                    sender.sendMessage("§aFastMinecarts configuration reloaded.")
                    return true
                }
            } else {
                sender.sendMessage("§cYou do not have permission to reload FastMinecarts.")
            }
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String>? {
        if (command.name.equals("FastMinecarts", ignoreCase = true)) {
            if (args.size == 1) {
                return listOf("reload").filter { it.startsWith(args[0], ignoreCase = true) }
            }
        }
        return null
    }
}