package fyi.fyw.mc.pluginnonebot.models

import com.google.gson.annotations.SerializedName
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class NPlayer(
    @SerializedName("display_name") val displayName: String,
    @SerializedName("player_list_name") val playerListName: String,
    @SerializedName("player_list_header") val playerListHeader: String,
    @SerializedName("player_list_footer") val playerListFooter: String,

    val address: String,
    val ping: Int,
    val locale: String,

    val sneaking: Boolean,
    val sprinting: Boolean,
    val flying: Boolean,

    @SerializedName("bed_spawn_location") val bedSpawnLocation: NLocation?,

    val exp: Float,
    val level: Int,
    @SerializedName("total_experience") val totalExperience: Int,

    velocity: NVelocity,
    location: NLocation,
    height: Double,
    width: Double,
    onGround: Boolean,
    inWater: Boolean,
    entityId: Int,
    fireTicks: Int,
    maxFireTicks: Int,

    vehicle: NEntity?,
    passengers: List<NEntity>?,
    empty: Boolean,
    insideVehicle: Boolean,

    ticksLived: Int,
    entityType: String,

    customNameVisible: Boolean,
    customName: String?,

    invulnerable: Boolean,
    scoreboardTags: Set<String>,
) : NEntity(
    velocity,
    location,
    height,
    width,
    onGround,
    inWater,
    entityId,
    fireTicks,
    maxFireTicks,

    vehicle,
    passengers,
    empty,
    insideVehicle,

    ticksLived,
    entityType,

    customNameVisible,
    customName,

    invulnerable,
    scoreboardTags,
) {
    companion object {
        fun from(player: OfflinePlayer): NPlayer {
            return when (player) {
                is Player -> NPlayer(
                    player.displayName,
                    player.playerListName,
                    player.playerListHeader ?: "",
                    player.playerListFooter ?: "",

                    player.address.toString(),
                    player.ping,
                    player.locale,

                    player.isSneaking,
                    player.isSprinting,
                    player.isFlying,

                    player.bedSpawnLocation?.let { NLocation.from(it) },

                    player.exp,
                    player.level,
                    player.totalExperience,

                    NVelocity.from(player.velocity),
                    NLocation.from(player.location),
                    player.height,
                    player.width,
                    player.isOnGround,
                    player.isInWater,
                    player.entityId,
                    player.fireTicks,
                    player.maxFireTicks,
                    player.vehicle?.let { NEntity.from(it) },
                    player.passengers.map { NEntity.from(it) },
                    player.isEmpty,
                    player.isInsideVehicle,
                    player.ticksLived,
                    player.type.name,
                    player.isCustomNameVisible,
                    player.customName,
                    player.isInvulnerable,
                    player.scoreboardTags,
                )

                else -> NPlayer(
                    player.name ?: "",
                    player.name ?: "",
                    "",
                    "",

                    "",
                    0,
                    "",

                    false,
                    false,
                    false,

                    null,

                    0f,
                    0,
                    0,

                    NVelocity(0.0, 0.0, 0.0),
                    NLocation("Unknown World", 0.0, 0.0, 0.0, 0.0f, 0.0f),
                    0.0,
                    0.0,
                    false,
                    false,
                    0,
                    0,
                    0,
                    null,
                    null,
                    true,
                    false,
                    0,
                    "",
                    false,
                    null,
                    false,
                    emptySet(),
                )
            }
        }
    }
}
