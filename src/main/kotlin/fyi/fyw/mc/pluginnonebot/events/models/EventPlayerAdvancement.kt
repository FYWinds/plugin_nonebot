package fyi.fyw.mc.pluginnonebot.events.models

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class EventPlayerAdvancement(
    player: NSimplePlayer,
    val advancement: String,
) : BaseEvent {
    companion object {
        fun from(event: PlayerAdvancementDoneEvent): EventPlayerAdvancement {
            return EventPlayerAdvancement(
                NSimplePlayer.from(event.player),
                event.advancement.key.toString(),
            )
        }
    }
}
