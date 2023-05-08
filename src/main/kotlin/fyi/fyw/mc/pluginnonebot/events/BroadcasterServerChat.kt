package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.events.models.EventServerChat
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.server.BroadcastMessageEvent

class BroadcasterServerChat : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: BroadcastMessageEvent) {
        broadcast(
            BaseEventFrame(
                data = EventServerChat.from(event),
                type = "message",
                detailType = "server_broadcast",
            ),
        )
    }
}
