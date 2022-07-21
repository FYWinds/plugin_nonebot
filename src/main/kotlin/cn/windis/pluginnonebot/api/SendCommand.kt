package cn.windis.pluginnonebot.api

import cn.windis.pluginnonebot.PluginNonebot
import org.bukkit.Bukkit

fun sendCommand(command: String): List<String> {
    val sender = ConsoleSender()
    Bukkit.getScheduler().runTask(PluginNonebot.instance, Runnable {
        Bukkit.getServer().dispatchCommand(sender, command)
    })
    return sender.getMessages() // 实际测试发现毫无用处
}