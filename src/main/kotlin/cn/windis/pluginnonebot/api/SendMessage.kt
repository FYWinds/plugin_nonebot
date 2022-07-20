package cn.windis.pluginnonebot.api

import org.bukkit.Bukkit

fun sendMessage(message: String) {
    Bukkit.getServer().broadcastMessage(message)
}