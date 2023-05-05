package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.models.NEntityLocation
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import fyi.fyw.mc.pluginnonebot.models.event.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.models.event.EventPlayerQuit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent

class BroadcasterPlayerQuit : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: PlayerQuitEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerQuit(
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
                    event.quitMessage ?: "",
                ),
                type = "player",
                detailType = "player_quit",
            ),

        )
    }
}