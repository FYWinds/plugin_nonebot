package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
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
                    NSimplePlayer.fromPlayer(event.player),
                    event.quitMessage ?: "",
                ),
                type = "player",
                detailType = "player_quit",
            ),

        )
    }
}
