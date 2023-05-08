package fyi.fyw.mc.pluginnonebot.api.internal

import fyi.fyw.mc.pluginnonebot.api.ApiHandler
import fyi.fyw.mc.pluginnonebot.models.api.BaseApiResult
import fyi.fyw.mc.pluginnonebot.models.api.ResultGetPlayerInventory
import org.bukkit.Bukkit
import java.util.*

class HandlerGetPlayerInventory : ApiHandler {
    override val id: String = "get_player_inventory"
    private val uuidPattern: Regex = Regex("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$")
    override fun handle(params: Map<String, Any>): BaseApiResult {
        val playerId = params.getOrDefault("player_id", "") as String
        val type = params.getOrDefault("type", "inventory") as String // inventory or ender_chest
        if (uuidPattern.matches(playerId)) {
            Bukkit.getPlayer(UUID.fromString(playerId))?.let {
                return when (type) {
                    "inventory" -> ResultGetPlayerInventory.from(it.inventory)
                    "ender_chest" -> ResultGetPlayerInventory.from(it.enderChest)
                    else -> {
                        throw Exception("Unknown inventory type")
                    }
                }
            } ?: throw Exception("Player not found online")
        } else {
            Bukkit.getPlayer(playerId)?.let {
                return when (type) {
                    "inventory" -> ResultGetPlayerInventory.from(it.inventory)
                    "ender_chest" -> ResultGetPlayerInventory.from(it.enderChest)
                    else -> {
                        throw Exception("Unknown inventory type")
                    }
                }
            } ?: throw Exception("Player not found online")
        }
    }
}
