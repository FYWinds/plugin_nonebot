package cn.windis.pluginnonebot.api

import cn.windis.pluginnonebot.PluginNonebot
import org.bukkit.Bukkit

fun sendCommand(command: String): Boolean {
    val sender = ConsoleSender()
    val task = Bukkit.getScheduler()
        .callSyncMethod(
            PluginNonebot.instance
        ) { Bukkit.getServer().dispatchCommand(sender, command) }
    return task.get()
}