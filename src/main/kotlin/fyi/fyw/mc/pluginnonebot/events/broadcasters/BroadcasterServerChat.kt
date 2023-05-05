package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.models.NEntityLocation
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
                    event.recipients.filterIsInstance<Player>().map {
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
                    message = event.message,
                    event.isCancelled,
                ),
                type = "message",
                detailType = "server_broadcast",
            ),
        )
    }
}
