package fyi.fyw.mc.pluginnonebot.models

import com.google.gson.annotations.SerializedName
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

open class NEntity(
    val velocity: NVelocity,
    val location: NLocation,
    val height: Double,
    val width: Double,
    @SerializedName("on_ground") val onGround: Boolean,
    @SerializedName("in_water") val inWater: Boolean,
    @SerializedName("entity_id") val entityId: Int,
    @SerializedName("fire_ticks") val fireTicks: Int,
    @SerializedName("max_fire_ticks") val maxFireTicks: Int,

    val vehicle: NEntity?,
    val passengers: List<NEntity>?,
    val empty: Boolean,
    @SerializedName("inside_vehicle") val insideVehicle: Boolean,

    @SerializedName("ticks_lived") val ticksLived: Int,
    @SerializedName("entity_type") val entityType: String,

    @SerializedName("custom_name_visible") val customNameVisible: Boolean,
    @SerializedName("custom_name") val customName: String?,

    val invulnerable: Boolean,
    @SerializedName("scoreboard_tags") val scoreboardTags: Set<String>,
) {

    companion object {
        fun from(entity: Entity): NEntity {
            return NEntity(
                NVelocity.from(entity.velocity),
                NLocation.from(entity.location),
                entity.height,
                entity.width,
                entity.isOnGround,
                entity.isInWater,
                entity.entityId,
                entity.fireTicks,
                entity.maxFireTicks,
                entity.vehicle?.let { NEntity.from(it) },
                entity.passengers.map { NEntity.from(it) },
                entity.isEmpty,
                entity.isInsideVehicle,
                entity.ticksLived,
                entity.type.name,
                entity.isCustomNameVisible,
                entity.customName,
                entity.isInvulnerable,
                entity.scoreboardTags,
            )
        }
    }

    class NVelocity(
        val x: Double,
        val y: Double,
        val z: Double,
    ) {
        companion object {
            fun from(velocity: Vector): NVelocity {
                return NVelocity(
                    velocity.x,
                    velocity.y,
                    velocity.z,
                )
            }
        }
    }
}
