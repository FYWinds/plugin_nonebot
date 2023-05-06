package fyi.fyw.mc.pluginnonebot.models.event

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class EventPlayerCommand(
    val sender: NSimplePlayer,
    val command: String,
    val cancelled: Boolean,
) : BaseEvent {
    companion object {
        fun from(event: PlayerCommandPreprocessEvent): EventPlayerCommand {
            return EventPlayerCommand(
                NSimplePlayer.from(event.player),
                event.message,
                event.isCancelled,
            )
        }
    }
}
