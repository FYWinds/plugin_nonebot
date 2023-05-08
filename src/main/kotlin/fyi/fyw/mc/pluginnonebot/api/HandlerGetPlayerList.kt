package fyi.fyw.mc.pluginnonebot.api

import fyi.fyw.mc.pluginnonebot.api.models.BaseApiResult
import fyi.fyw.mc.pluginnonebot.api.models.ResultGetPlayerList
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.Bukkit

class HandlerGetPlayerList : ApiHandler {
    override val id: String = "get_player_list"
    override fun handle(params: Map<String, Any>): BaseApiResult {
        val offline = params.getOrDefault("offline", false) as Boolean
        return ResultGetPlayerList(
            if (offline) {
                NSimplePlayer.from(Bukkit.getOfflinePlayers().toList())
            } else {
                NSimplePlayer.from(Bukkit.getOnlinePlayers().toList())
            },
        )
    }
}
