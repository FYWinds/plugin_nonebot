package fyi.fyw.mc.pluginnonebot.models

import org.bukkit.Location
import java.io.Serializable

open class NEntityLocation(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float,
) : Serializable {

    companion object {
        fun fromLocation(location: Location): NEntityLocation {
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
