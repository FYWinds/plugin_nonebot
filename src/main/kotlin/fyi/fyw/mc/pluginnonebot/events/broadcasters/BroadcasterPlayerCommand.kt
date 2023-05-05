package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
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
                    NSimplePlayer.fromPlayer(event.player),
                    event.message,
                    event.isCancelled,
                ),
                type = "command",
                detailType = "player_command",
            ),
        )
    }
}
