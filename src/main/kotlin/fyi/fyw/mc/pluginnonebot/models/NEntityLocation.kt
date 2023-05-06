package fyi.fyw.mc.pluginnonebot.models

import org.bukkit.Location

open class NEntityLocation(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float,
) {

    companion object {
        fun from(location: Location): NEntityLocation {
            return NEntityLocation(
                location.world!!.name,
                location.x,
                location.y,
                location.z,
                location.yaw,
                location.pitch,
            )
        }
    }
}
