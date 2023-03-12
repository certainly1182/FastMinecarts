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
    private var _blocks = listOf(Material.GRAVEL)
    private var _speedMultiplier = 2.0
    private val railTypes = listOf(
        Material.RAIL, Material.POWERED_RAIL,
        Material.DETECTOR_RAIL, Material.ACTIVATOR_RAIL
    )
    override fun onEnable() {
        saveDefaultConfig()
        _blocks = config.getStringList("blocks").map{
            Material.getMaterial(it) ?: Material.GRAVEL
        }
        _speedMultiplier = config.getDouble("speed-multiplier", 2.0)
        Bukkit.getPluginManager().registerEvents(this, this)
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
        if (blockBelow.type in _blocks) {
            minecart.maxSpeed *= _speedMultiplier
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onVehicleExit(event: VehicleExitEvent) {
        if (event.vehicle !is Minecart) return
        if (event.exited !is Player) return

        val minecart = event.vehicle as Minecart
        if (minecart.maxSpeed > 0.4) {  // vanilla max speed
            minecart.maxSpeed = 0.4
        }
    }
}