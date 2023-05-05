package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
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
                    NSimplePlayer.fromPlayer(event.player),
                    event.joinMessage ?: "",
                ),
                type = "player",
                detailType = "player_join",
            ),

        )
    }
}
