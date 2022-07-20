package cn.windis.pluginnonebot.api

import org.bukkit.Bukkit

fun sendCommand(command: String) {
    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command)
}