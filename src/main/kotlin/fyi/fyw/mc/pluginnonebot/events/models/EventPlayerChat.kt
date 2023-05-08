package fyi.fyw.mc.pluginnonebot.events.models

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.event.player.AsyncPlayerChatEvent

class EventPlayerChat(
    val sender: NSimplePlayer,
    val recipients: Set<NSimplePlayer>,
    val message: String,
    val cancelled: Boolean,
) : BaseEvent {
    companion object {
        fun from(event: AsyncPlayerChatEvent): EventPlayerChat {
            return EventPlayerChat(
                NSimplePlayer.from(event.player),
                NSimplePlayer.from(event.recipients),
                event.message,
                event.isCancelled,
            )
        }
    }
}
