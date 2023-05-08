package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.events.models.EventPlayerChat
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent

class BroadcasterPlayerChat : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: AsyncPlayerChatEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerChat.from(event),
                type = "message",
                detailType = "player_chat",
            ),
        )
    }
}
