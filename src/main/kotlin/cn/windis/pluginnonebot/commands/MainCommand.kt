package cn.windis.pluginnonebot.commands

import cn.windis.pluginnonebot.PluginNonebot
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class MainCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args == null) {
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage("§c请输入子命令")
            return false
        }
        when (args[0]) {
            "reload" -> {
                PluginNonebot.instance.reload()
                sender.sendMessage("§a重载成功")
                return true
            }
            "help" -> {
                sender.sendMessage(getHelp())
                return true
            }
            "start" -> {
                if (PluginNonebot.started) {
                    sender.sendMessage("§c已经启动")
                    return true
                }
                PluginNonebot.instance.connect()
                sender.sendMessage("§a启动成功")
                return true
            }
            "stop" -> {
                if (!PluginNonebot.started) {
                    sender.sendMessage("§c未启动")
                    return true
                }
                PluginNonebot.instance.disconnect()
                sender.sendMessage("[PluginNonebot] §a关闭成功")
                return true
            }
            else -> {
                sender.sendMessage(getHelp())
                return true
            }
        }
    }

    private fun getHelp(): String {
        return """§f[§aPlugin§cNonebot §fv${PluginNonebot.instance.description.version} §a帮助§f]
            §a/pn reload §7重载配置文件
            §a/pn help   §7显示帮助信息
            §a/pn start  §7开启插件
            §a/pn stop   §7关闭插件
        """.trimIndent()
    }

    companion object {
        private val PREFIX_PN = "§f[§aPlugin§cNonebot§f] "
    }
}