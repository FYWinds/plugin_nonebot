package fyi.fyw.mc.pluginnonebot.api.models

import fyi.fyw.mc.pluginnonebot.models.NItem
import org.bukkit.inventory.Inventory

class ResultGetPlayerInventory(
    val items: List<NItem> = emptyList(),
) : BaseApiResult {
    companion object {
        fun from(inv: Inventory): ResultGetPlayerInventory {
            return ResultGetPlayerInventory(
                inv.toList().mapNotNull { it?.let { NItem.from(it) } },
            )
        }
    }
}
