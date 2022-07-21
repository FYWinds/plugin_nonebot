package cn.windis.pluginnonebot.api

import cn.windis.pluginnonebot.model.NEntityLocation
import cn.windis.pluginnonebot.model.NPlayerInfo
import cn.windis.pluginnonebot.model.NPlayerInventory
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

fun getPlayerInfo(playerIdentifier: String): NPlayerInfo? {
    var player: Player? = null
    player = if (TempPlayerInfo.uuidPattern.matches(playerIdentifier)) {
        Bukkit.getPlayer(UUID.fromString(playerIdentifier))
    } else {
        Bukkit.getPlayer(playerIdentifier)
    }
    if (player == null) {
        return null
    }

    return NPlayerInfo(
        name = player.name,
        uuid = player.uniqueId.toString(),
        displayName = player.displayName,
        location = NEntityLocation(
            x = player.location.x,
            y = player.location.y,
            z = player.location.z,
            world = player.location.world?.name ?: ""
        ),
        spawnLocation = player.bedSpawnLocation?.let {
            NEntityLocation(
                x = it.x,
                y = it.y,
                z = it.z,
                world = it.world?.name ?: ""
            )
        },
        online = player.isOnline,
        lastPlayed = player.lastPlayed,
        firstPlayed = player.firstPlayed,
        address = player.address?.address?.hostAddress.toString(),
        inventory = player.inventory.let { inventory ->
            NPlayerInventory(
                items = inventory.contents.mapIndexed { index, item -> item?.let { index to it.serialize() } }
                    .filterNotNull().toMap(),
                armor = inventory.armorContents.mapIndexed { index, item -> item?.let { index to it.serialize() } }
                    .filterNotNull().toMap(),
                extra = inventory.extraContents.mapIndexed { index, item -> item?.let { index to it.serialize() } }
                    .filterNotNull().toMap(),
                mainHand = inventory.itemInMainHand.serialize(),
                offHand = inventory.itemInOffHand.serialize(),
                heldItemSlot = inventory.heldItemSlot,
            )
        },
        walkSpeed = player.walkSpeed,
        flySpeed = player.flySpeed,
        canFly = player.allowFlight,
        exp = player.totalExperience,
        locale = player.locale,
        ping = player.ping,
        gamemode = player.gameMode.name,
    )
}

class TempPlayerInfo {
    companion object {
        val uuidPattern = Regex("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
    }
}