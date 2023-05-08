package fyi.fyw.mc.pluginnonebot.events

import fyi.fyw.mc.pluginnonebot.events.models.BaseEventFrame
import fyi.fyw.mc.pluginnonebot.events.models.EventPlayerQuit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent

class BroadcasterPlayerQuit : EventBroadcaster() {
    @EventHandler(priority = EventPriority.MONITOR)
    fun listen(event: PlayerQuitEvent) {
        broadcast(
            BaseEventFrame(
                data = EventPlayerQuit.from(event),
                type = "player",
                detailType = "player_quit",
            ),

        )
    }
}
