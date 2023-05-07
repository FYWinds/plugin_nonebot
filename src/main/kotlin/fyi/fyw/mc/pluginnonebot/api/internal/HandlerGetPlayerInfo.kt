package fyi.fyw.mc.pluginnonebot.api.internal

import fyi.fyw.mc.pluginnonebot.api.ApiHandler
import fyi.fyw.mc.pluginnonebot.models.NPlayer
import fyi.fyw.mc.pluginnonebot.models.api.BaseApiResult
import fyi.fyw.mc.pluginnonebot.models.api.ResultGetPlayerInfo
import org.bukkit.Bukkit
import java.util.*

class HandlerGetPlayerInfo : ApiHandler {
    override val id: String = "get_player_info"
    private val uuidPattern: Regex = Regex("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$")
    override fun handle(params: Map<String, Any>): BaseApiResult {
        val playerId = params.getOrDefault("player_id", "") as String
        return if (uuidPattern.matches(playerId)) {
            ResultGetPlayerInfo(
                NPlayer.from(
                    Bukkit.getPlayer(UUID.fromString(playerId)) ?: throw Exception("Player not found online"),
                ),
            )
        } else {
            ResultGetPlayerInfo(
                NPlayer.from(
                    Bukkit.getPlayer(playerId) ?: throw Exception("Player not found online"),
                ),
            )
        }
    }
}
