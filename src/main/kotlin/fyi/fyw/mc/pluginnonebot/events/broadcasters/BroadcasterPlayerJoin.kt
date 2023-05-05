package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.models.NEntityLocation
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import fyi.fyw.mc.pluginnonebot.models.event.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.models.event.EventPlayerJoin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerJoinEvent

class BroadcasterPlayerJoin : EventBroadcaster() {

    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: PlayerJoinEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerJoin(
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
                    event.joinMessage ?: "",
                ),
                type = "player",
                detailType = "player_join",
            ),

        )
    }
}
