package me.certainly1182.fastminecarts

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Minecart
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.vehicle.VehicleExitEvent
import org.bukkit.event.vehicle.VehicleMoveEvent
import org.bukkit.plugin.java.JavaPlugin

class FastMinecarts : JavaPlugin(), Listener {
    private val VANILLA_MAX_SPEED = 0.4
    private var _blockMaxSpeeds = mutableMapOf<Material, Double>()
    private val railTypes = listOf(
        Material.RAIL, Material.POWERED_RAIL,
        Material.DETECTOR_RAIL, Material.ACTIVATOR_RAIL
    )
    override fun onEnable() {
        saveDefaultConfig()
        loadConfig()
        Bukkit.getPluginManager().registerEvents(this, this)
    }
    private fun loadConfig() {
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
}