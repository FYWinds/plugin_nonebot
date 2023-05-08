package fyi.fyw.mc.pluginnonebot.models

import com.google.gson.annotations.SerializedName
import org.bukkit.inventory.ItemStack

class NItem(
    val material: String,
    val amount: Int,
    @SerializedName("item_meta") val itemMeta: Map<String, Any>,
) {
    companion object {
        fun from(item: ItemStack): NItem {
            return NItem(
                item.type.name,
                item.amount,
                item.itemMeta?.serialize() ?: mapOf(),
            )
        }
    }
}
