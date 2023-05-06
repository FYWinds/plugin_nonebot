package fyi.fyw.mc.pluginnonebot.models

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

open class NSimplePlayer(
    val name: String,
    val uuid: String,
    val isOnline: Boolean,
    val location: NEntityLocation?,
) {
    companion object {
        fun fromPlayer(player: Player): NSimplePlayer {
            return NSimplePlayer(
                player.displayName,
                player.uniqueId.toString(),
                player.isOnline,
                NEntityLocation.fromLocation(player.location),
            )
        }

        fun fromPlayer(players: List<OfflinePlayer>): Set<NSimplePlayer> {
            return players.map {
                NSimplePlayer(
                    it.name ?: "Unknown Player",
                    it.uniqueId.toString(),
                    it.isOnline,
                    if (it is Player && it.isOnline) {
                        NEntityLocation(
                            it.location.world!!.name,
                            it.location.x,
                            it.location.y,
                            it.location.z,
                            it.location.yaw,
                            it.location.pitch,
                        )
                    } else {
                        null
                    },
                )
            }.toSet()
        }
    }
}
