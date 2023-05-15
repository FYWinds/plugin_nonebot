package fyi.fyw.mc.pluginnonebot.models

import com.google.gson.annotations.SerializedName
import org.bukkit.entity.Player

class NPlayer(
    @SerializedName("display_name") val displayName: String,
    @SerializedName("player_list_name") val playerListName: String,

    val address: String,
    val locale: String,

    val sneaking: Boolean,
    val sprinting: Boolean,
    val flying: Boolean,

    val exp: Float,
    val level: Int,
    @SerializedName("total_experience") val totalExperience: Int,

    location: NLocation,

    ticksLived: Int,

    customNameVisible: Boolean,
    customName: String?,

    invulnerable: Boolean,
    scoreboardTags: Set<String>,
) {
    companion object {
        fun from(player: Player): NPlayer {
            return NPlayer(
                player.displayName,
                player.playerListName,

                player.address.toString(),
                player.locale,

                player.isSneaking,
                player.isSprinting,
                player.isFlying,

                player.exp,
                player.level,
                player.totalExperience,

                NLocation.from(player.location),
                player.ticksLived,
                player.isCustomNameVisible,
                player.customName,
                player.isInvulnerable,
                player.scoreboardTags,
            )
        }
    }
}
