package fyi.fyw.mc.pluginnonebot.models

import org.bukkit.command.CommandSender

class NSimpleCommandSender(val name: String) {
    companion object {
        fun from(sender: CommandSender): NSimpleCommandSender {
            return NSimpleCommandSender(sender.name)
        }
    }
}
