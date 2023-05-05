package fyi.fyw.mc.pluginnonebot.models

import java.io.Serializable

open class NEntityLocation(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float,
) : Serializable