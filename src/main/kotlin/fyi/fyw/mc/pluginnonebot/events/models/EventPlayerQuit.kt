package fyi.fyw.mc.pluginnonebot.events.models

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.event.player.PlayerQuitEvent

class EventPlayerQuit(val player: NSimplePlayer, val message: String) : BaseEvent {
    companion object {
        fun from(event: PlayerQuitEvent): EventPlayerQuit {
            return EventPlayerQuit(
                NSimplePlayer.from(event.player),
                event.quitMessage ?: "",
            )
        }
    }
}
