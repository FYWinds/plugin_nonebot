package fyi.fyw.mc.pluginnonebot.events.broadcasters

import fyi.fyw.mc.pluginnonebot.events.EventBroadcaster
import fyi.fyw.mc.pluginnonebot.models.NEntityLocation
import fyi.fyw.mc.pluginnonebot.models.NEventPlayerLogin
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent

class BroadcasterPlayerLogin : EventBroadcaster() {

    @EventHandler
    fun listen(event: PlayerJoinEvent) {
        broadcast(
            NEventPlayerLogin(
                NSimplePlayer(
                    event.player.name,
                    event.player.uniqueId.toString(),
                    true,
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
        )
    }
}
