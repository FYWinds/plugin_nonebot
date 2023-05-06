package fyi.fyw.mc.pluginnonebot.models.event

import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.entity.Player
import org.bukkit.event.server.BroadcastMessageEvent

class EventServerChat(val recipients: Set<NSimplePlayer>, val message: String, val cancelled: Boolean) : BaseEvent {
    companion object {
        fun from(event: BroadcastMessageEvent): EventServerChat {
            return EventServerChat(
                NSimplePlayer.from(event.recipients.filterIsInstance<Player>()),
                event.message,
                event.isCancelled,
            )
        }
    }
}
