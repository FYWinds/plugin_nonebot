package cn.windis.pluginnonebot.event

import com.google.gson.annotations.SerializedName


data class NEntityLocation(
    @SerializedName("x") val x: Double,
    @SerializedName("y") val y: Double,
    @SerializedName("z") val z: Double,
    @SerializedName("world") val world: String
)

data class NSimpleEntity(
    @SerializedName("name") val name: String,
    @SerializedName("entity_id") val eid: String,
    @SerializedName("unique_id") val uid: String,
    @SerializedName("location") val location: NEntityLocation,
)

data class NSimplePlayer(
    @SerializedName("name") val name: String,
    @SerializedName("uuid") val uuid: String,
    @SerializedName("location") val location: NEntityLocation,
    @SerializedName("online") val online: Boolean
)

data class NAdvancement(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: String,
)

data class NPlayerJoinEvent(
    @SerializedName("player") val player: NSimplePlayer,
    @SerializedName("join_message") val joinMessage: String
)

data class NPlayerQuitEvent(
    @SerializedName("player") val player: NSimplePlayer,
    @SerializedName("quit_message") val quitMessage: String
)

data class NPlayerDeathEvent(
    @SerializedName("player") val player: NSimplePlayer,
    @SerializedName("death_message") val deathMessage: String,
    @SerializedName("death_cause") val deathCause: String,
)

data class NExplosionPrimeEvent(
    @SerializedName("entity") val entity: NSimpleEntity,
    @SerializedName("radius") val radius: Double,
    @SerializedName("fire") val fire: Boolean,
)

data class NPlayerChatEvent(
    @SerializedName("player") val player: NSimplePlayer,
    @SerializedName("message") val message: String,
    @SerializedName("shown_to") val shownTo: List<NSimplePlayer>,
)

data class NPlayerCommandSendEvent(
    @SerializedName("player") val player: NSimplePlayer,
    @SerializedName("command") val command: String,
)

data class NPlayerAdvancementDoneEvent(
    @SerializedName("player") val player: NSimplePlayer,
    @SerializedName("advancement") val advancement: NAdvancement,
)