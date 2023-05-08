package fyi.fyw.mc.pluginnonebot.api

import fyi.fyw.mc.pluginnonebot.api.models.BaseApiResult
import fyi.fyw.mc.pluginnonebot.api.models.ResultGetPlayerInventory
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class HandlerGetPlayerInventory : ApiHandler {
    override val id: String = "get_player_inventory"
    private val uuidPattern: Regex = Regex("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$")

    @Throws(Exception::class)
    override fun handle(params: Map<String, Any>): BaseApiResult {
        val playerId = params.getOrDefault("player_id", "") as String
        val type = params.getOrDefault("type", "inventory") as String // inventory or ender_chest
        if (uuidPattern.matches(playerId)) {
            Bukkit.getPlayer(UUID.fromString(playerId))?.let {
                return getInventory(it, type)
            } ?: throw Exception("Player not found online")
        } else {
            Bukkit.getPlayer(playerId)?.let {
                return getInventory(it, type)
            } ?: throw Exception("Player not found online")
        }
    }

    private fun getInventory(player: Player, type: String): BaseApiResult {
        return when (type) {
            "inventory" -> ResultGetPlayerInventory.from(player.inventory)
            "ender_chest" -> ResultGetPlayerInventory.from(player.enderChest)
            else -> {
                throw Exception("Unknown inventory type")
            }
        }
    }
}
