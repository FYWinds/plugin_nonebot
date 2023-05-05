package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import fyi.fyw.mc.pluginnonebot.models.event.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.models.event.EventServerChat
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.server.BroadcastMessageEvent

class BroadcasterServerChat : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: BroadcastMessageEvent) {
        broadcast(
            BaseEventFrame(
                data = EventServerChat(
                    NSimplePlayer.fromPlayer(event.recipients.filterIsInstance<Player>()),
                    message = event.message,
                    event.isCancelled,
                ),
                type = "message",
                detailType = "server_broadcast",
            ),
        )
    }
}
