package cn.windis.pluginnonebot.event

import cn.windis.pluginnonebot.PluginNonebot.Companion.getWsConnection
import cn.windis.pluginnonebot.model.*
import cn.windis.pluginnonebot.utils.MessageBuilder
import com.google.gson.Gson
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ExplosionPrimeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


class MainEventListener : Listener {
    private val gson = Gson()

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val data = gson.toJson(
            NPlayerJoinEvent(
                player = NSimplePlayer(
                    name = event.player.name,
                    uuid = event.player.uniqueId.toString(),
                    location = NEntityLocation(
                        x = event.player.location.x,
                        y = event.player.location.y,
                        z = event.player.location.z,
                        world = event.player.location.world!!.name
                    ),
                    online = true
                ),
                joinMessage = event.joinMessage ?: ""
            )
        )
        val message = MessageBuilder("notice").detailType("player_event").subType("join")
            .id(event.hashCode().toString()).data(data)
        getWsConnection().broadcast(message)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val data = gson.toJson(
            NPlayerQuitEvent(
                player = NSimplePlayer(
                    name = event.player.name,
                    uuid = event.player.uniqueId.toString(),
                    location = NEntityLocation(
                        x = event.player.location.x,
                        y = event.player.location.y,
                        z = event.player.location.z,
                        world = event.player.location.world!!.name
                    ),
                    online = false
                ),
                quitMessage = event.quitMessage ?: ""
            )
        )
        val message = MessageBuilder("notice").detailType("player_event").subType("quit")
            .id(event.hashCode().toString()).data(data)
        getWsConnection().broadcast(message)
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val lastDamageCauseName = player.lastDamageCause?.cause?.name ?: return
        val data = gson.toJson(
            NPlayerDeathEvent(
                player = NSimplePlayer(
                    name = player.name,
                    uuid = player.uniqueId.toString(),
                    location = NEntityLocation(
                        x = player.location.x,
                        y = player.location.y,
                        z = player.location.z,
                        world = player.location.world!!.name
                    ),
                    online = player.isOnline
                ),
                deathMessage = event.deathMessage ?: "",
                deathCause = lastDamageCauseName
            )
        )
        val message = MessageBuilder("notice").detailType("player_event").subType("death")
            .id(event.hashCode().toString()).data(data)
        getWsConnection().broadcast(message)
    }

    @EventHandler
    fun onExplosionPrime(event: ExplosionPrimeEvent) {
        val data = gson.toJson(
            NExplosionPrimeEvent(
                radius = event.radius.toDouble(),
                fire = event.fire,
                entity = NSimpleEntity(
                    name = event.entity.name,
                    eid = event.entity.entityId.toString(),
                    uid = event.entity.uniqueId.toString(),
                    location = NEntityLocation(
                        x = event.entity.location.x,
                        y = event.entity.location.y,
                        z = event.entity.location.z,
                        world = event.entity.location.world!!.name
                    )
                ),
            )
        )
        val message = MessageBuilder("notice").detailType("entity_event").subType("explosion")
            .id(event.hashCode().toString()).data(data)
        getWsConnection().broadcast(message)
    }


    @EventHandler
    fun onAsyncPlayerChat(event: AsyncPlayerChatEvent) {
        val data = gson.toJson(
            NPlayerChatEvent(
                player = NSimplePlayer(
                    name = event.player.name,
                    uuid = event.player.uniqueId.toString(),
                    location = NEntityLocation(
                        x = event.player.location.x,
                        y = event.player.location.y,
                        z = event.player.location.z,
                        world = event.player.location.world!!.name
                    ),
                    online = event.player.isOnline
                ),
                message = event.message,
                shownTo = event.recipients.map {
                    NSimplePlayer(
                        name = it.name,
                        uuid = it.uniqueId.toString(),
                        location = NEntityLocation(
                            x = it.location.x,
                            y = it.location.y,
                            z = it.location.z,
                            world = it.location.world!!.name
                        ),
                        online = it.isOnline
                    )
                }
            )
        )
        val message = MessageBuilder("message").detailType("player_message").subType("chat")
            .id(event.hashCode().toString()).data(data)
        getWsConnection().broadcast(message)
    }

    @EventHandler
    fun onPlayerCommandSend(event: PlayerCommandPreprocessEvent) {
        val data = gson.toJson(
            NPlayerCommandSendEvent(
                player = NSimplePlayer(
                    name = event.player.name,
                    uuid = event.player.uniqueId.toString(),
                    location = NEntityLocation(
                        x = event.player.location.x,
                        y = event.player.location.y,
                        z = event.player.location.z,
                        world = event.player.location.world!!.name
                    ),
                    online = event.player.isOnline
                ),
                command = event.message
            )
        )

        val message = MessageBuilder("message").detailType("player_message").subType("command")
            .id(event.hashCode().toString()).data(data)
        getWsConnection().broadcast(message)
    }
//
//    @EventHandler
//    fun onPlayerAdvancementDone(event: PlayerAdvancementDoneEvent) {
//        val data = gson.toJson(
//            NPlayerAdvancementDoneEvent(
//                player = NSimplePlayer(
//                    name = event.player.name,
//                    uuid = event.player.uniqueId.toString(),
//                    location = NEntityLocation(
//                        x = event.player.location.x,
//                        y = event.player.location.y,
//                        z = event.player.location.z,
//                        world = event.player.location.world!!.name
//                    ),
//                    online = event.player.isOnline
//                ),
//                advancement = event.advancement.display
//            )
//        )
//
//        val message = MessageBuilder("notice").detailType("player_event").subType("advancement")
//            .id(event.hashCode().toString()).data(data)
//        getWsConnection().broadcast(message)
//    }
}

