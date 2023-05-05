package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.models.NEntityLocation
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import fyi.fyw.mc.pluginnonebot.models.event.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.models.event.EventPlayerCommand
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class BroadcasterPlayerCommand : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: PlayerCommandPreprocessEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerCommand(
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
                    event.message,
                    event.isCancelled,
                ),
                type = "command",
                detailType = "player_command",
            ),
        )
    }
}
