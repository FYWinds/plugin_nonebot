package cn.windis.pluginnonebot.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player


class TabHandler : TabCompleter {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        if (sender !is Player) {
            // 控制台注册个鬼
            return null;
        }
        return mutableListOf("reload", "help", "start", "stop")
    }
}