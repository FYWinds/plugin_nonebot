package cn.windis.pluginnonebot.model

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

data class NPlayer(
    @SerializedName("name") val name: String,
    @SerializedName("uuid") val uuid: String,
    @SerializedName("location") val location: NEntityLocation?,
    @SerializedName("online") val online: Boolean,
    @SerializedName("last_played") val lastPlayed: Long,
    @SerializedName("first_played") val firstPlayed: Long,
    @SerializedName("address") val address: String?,
)

data class NPlayerInventory(
    @SerializedName("items") val items: Map<Int, Map<String, Any>>,
    @SerializedName("armor") val armor: Map<Int, Map<String, Any>>,
    @SerializedName("extra") val extra: Map<Int, Map<String, Any>>,
    @SerializedName("main_hand") val mainHand: Map<String, Any>,
    @SerializedName("off_hand") val offHand: Map<String, Any>,
    @SerializedName("held_itemslot") val heldItemSlot: Int,
)


data class NPlayerInfo(
    @SerializedName("name") val name: String,
    @SerializedName("uuid") val uuid: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("location") val location: NEntityLocation?,
    @SerializedName("spawn_location") val spawnLocation: NEntityLocation?,
    @SerializedName("online") val online: Boolean,
    @SerializedName("last_played") val lastPlayed: Long,
    @SerializedName("first_played") val firstPlayed: Long,
    @SerializedName("address") val address: String?,
    @SerializedName("inventory") val inventory: NPlayerInventory,
    @SerializedName("walk_speed") val walkSpeed: Float,
    @SerializedName("can_fly") val canFly: Boolean,
    @SerializedName("fly_speed") val flySpeed: Float,
    @SerializedName("exp") val exp: Int,
    @SerializedName("locale") val locale: String,
    @SerializedName("ping") val ping: Int,
    @SerializedName("gamemode") val gamemode: String,
)