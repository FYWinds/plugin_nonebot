package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import fyi.fyw.mc.pluginnonebot.models.event.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.models.event.EventPlayerChat
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent

class BroadcasterPlayerChat : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: AsyncPlayerChatEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerChat(
                    NSimplePlayer.fromPlayer(event.player),
                    NSimplePlayer.fromPlayer(event.recipients.filterIsInstance<Player>()),
                    event.message,
                    event.isCancelled,
                ),
                type = "message",
                detailType = "player_chat",
            ),
        )
    }
}
