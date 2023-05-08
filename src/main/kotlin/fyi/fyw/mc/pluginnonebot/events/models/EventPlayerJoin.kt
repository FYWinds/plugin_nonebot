package fyi.fyw.mc.pluginnonebot.events.models

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.event.player.PlayerJoinEvent

class EventPlayerJoin(
    val player: NSimplePlayer,
    val message: String,
) : BaseEvent {
    companion object {
        fun from(event: PlayerJoinEvent): EventPlayerJoin {
            return EventPlayerJoin(
                NSimplePlayer.from(event.player),
                event.joinMessage ?: "",
            )
        }
    }
}
