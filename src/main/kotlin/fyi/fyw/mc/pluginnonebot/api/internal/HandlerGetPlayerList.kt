package fyi.fyw.mc.pluginnonebot.api.internal

import fyi.fyw.mc.pluginnonebot.api.ApiHandler
import fyi.fyw.mc.pluginnonebot.models.NEntityLocation
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import fyi.fyw.mc.pluginnonebot.models.api.BaseApiResult
import fyi.fyw.mc.pluginnonebot.models.api.ResultGetPlayerList
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class HandlerGetPlayerList : ApiHandler {
    override val id: String = "get_player_list"
    override fun handle(params: Map<String, Any>): BaseApiResult {
        val offline = params.getOrDefault("offline", false) as Boolean
        return ResultGetPlayerList(
            if (offline) {
                Bukkit.getOfflinePlayers().map {
                    NSimplePlayer(
                        it.name ?: "Unknown Player",
                        it.uniqueId.toString(),
                        it.isOnline,
                        if (it is Player && it.isOnline) {
                            NEntityLocation(
                                it.location.world!!.name,
                                it.location.x,
                                it.location.y,
                                it.location.z,
                                it.location.yaw,
                                it.location.pitch,
                            )
                        } else {
                            null
                        },
                    )
                }.toSet()
            } else {
                Bukkit.getOnlinePlayers().map {
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
                }.toSet()
            },
        )
    }
}
