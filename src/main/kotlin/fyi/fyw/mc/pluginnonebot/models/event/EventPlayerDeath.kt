package fyi.fyw.mc.pluginnonebot.models.event

import com.google.gson.annotations.SerializedName
import fyi.fyw.mc.pluginnonebot.models.NSimplePlayer
import org.bukkit.event.entity.PlayerDeathEvent

class EventPlayerDeath(
    val player: NSimplePlayer,
    @SerializedName("death_message") val deathMessage: String,
    @SerializedName("new_exp") val newExp: Int,
    @SerializedName("new_level") val newLevel: Int,
    @SerializedName("new_total_exp") val newTotalExp: Int,
    @SerializedName("keep_level") val keepLevel: Boolean,
    @SerializedName("keep_inventory") val keepInventory: Boolean,
) : BaseEvent {
    companion object {
        fun from(event: PlayerDeathEvent): EventPlayerDeath {
            return EventPlayerDeath(
                NSimplePlayer.from(event.entity),
                event.deathMessage ?: "",
                event.newExp,
                event.newLevel,
                event.newTotalExp,
                event.keepLevel,
                event.keepInventory,
            )
        }
    }
}
