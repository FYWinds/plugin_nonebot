package fyi.fyw.mc.pluginnonebot.models.api

import fyi.fyw.mc.pluginnonebot.models.NItem
import org.bukkit.inventory.Inventory

class ResultGetPlayerInventory(
    val items: Map<Int, NItem?>,
) : BaseApiResult {
    companion object {
        fun from(inv: Inventory): ResultGetPlayerInventory {
            return ResultGetPlayerInventory(
                inv.mapIndexed { index, itemStack ->
                    index to itemStack?.let { NItem.from(it) }
                }.toMap(), // TODO: this won't give the correct result
            )
        }
    }
}
