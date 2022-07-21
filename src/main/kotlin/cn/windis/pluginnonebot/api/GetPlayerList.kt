package cn.windis.pluginnonebot.api

import cn.windis.pluginnonebot.model.NEntityLocation
import cn.windis.pluginnonebot.model.NPlayer
import org.bukkit.Bukkit

fun getPlayerList(online: Boolean): List<NPlayer> {
    val result = mutableListOf<NPlayer>()
    if (online) {
        Bukkit.getServer().onlinePlayers.map {
            result.add(
                NPlayer(
                    name = it.name,
                    uuid = it.uniqueId.toString(),
                    location = NEntityLocation(
                        x = it.location.x,
                        y = it.location.y,
                        z = it.location.z,
                        world = it.world.name
                    ),
                    online = it.isOnline,
                    lastPlayed = it.lastPlayed,
                    firstPlayed = it.firstPlayed,
                    address = it.address?.address?.hostAddress.toString(),
                )
            )
        }
    } else {
        Bukkit.getServer().offlinePlayers.map next@{
            result.add(
                NPlayer(
                    name = it.name ?: return@next,
                    uuid = it.uniqueId.toString(),
                    location = null,
                    online = it.isOnline,
                    lastPlayed = it.lastPlayed,
                    firstPlayed = it.firstPlayed,
                    address = it.player?.address?.address?.hostAddress.toString(),
                )
            )
        }
    }
    return result
}