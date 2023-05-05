package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.models.NEntityLocation
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import fyi.fyw.mc.pluginnonebot.models.event.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.models.event.EventPlayerChat
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent

class BroadcasterPlayerChat : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: AsyncPlayerChatEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerChat(
                    NSimplePlayer(
                        event.player.displayName,
                        event.player.uniqueId.toString(),
                        event.player.isOnline,
                        NEntityLocation(
                            event.player.location.world!!.name,
                            event.player.location.x,
                            event.player.location.y,
                            event.player.location.z,
                            event.player.location.yaw,
                            event.player.location.pitch,
                        ),
                    ),
                    event.recipients.map {
                        NSimplePlayer(
                            it.displayName,
                            it.uniqueId.toString(),
                            it.isOnline,
                            NEntityLocation(
                                it.location.world!!.name,
                                it.location.x,
                                it.location.y,
                                it.location.z,
                                it.location.yaw,
                                it.location.pitch,
                            ),
                        )
                    }.toSet(),
                    event.message,
                    event.isCancelled,
                ),
                type = "message",
                detailType = "player_chat",
            ),
        )
    }
}
