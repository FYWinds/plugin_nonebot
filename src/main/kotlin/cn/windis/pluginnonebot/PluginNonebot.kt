package cn.windis.pluginnonebot

import cn.windis.pluginnonebot.commands.MainCommand
import cn.windis.pluginnonebot.config.PluginConfig
import cn.windis.pluginnonebot.webSocket.client.Client
import cn.windis.pluginnonebot.webSocket.server.Server
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

/**
 * @author FYWinds
 */
class PluginNonebot : JavaPlugin() {

    override fun onEnable() {

        this.saveDefaultConfig()

        instance = this
        Companion.logger = this.logger
        pluginConfig = PluginConfig(this.config)
        if (pluginConfig.config.isAutoStart) {
            connect()
        }
        Bukkit.getPluginCommand("pluginnonebot")?.setExecutor(MainCommand())
        // TODO Support for multiple connections
        // Logger
    }

    override fun onDisable() {
        // Plugin shutdown logic
        disconnect()
    }

    fun connect() {
        when (pluginConfig.config.connectionType) {
            "ws" -> run {
                Companion.server =
                    Server(pluginConfig.config.wsConnection!!.host, pluginConfig.config.wsConnection!!.port)
            }
            "reverse-ws" -> {
                client = Client(
                    pluginConfig.config.rwsConnection!!.server,
                    pluginConfig.config.rwsConnection!!.retryInterval,
                    pluginConfig.config.rwsConnection!!.maxRetries
                )
            }
        }
        started = true
    }

    fun disconnect() {
        when (pluginConfig.config.connectionType) {
            "ws" -> {
                Companion.server.stop()
            }
            "reverse-ws" -> {
                client.stop()
            }
        }
        started = false
    }

    fun reload() {
        onDisable()
        onEnable()
    }

    companion object {
        lateinit var instance: PluginNonebot
        lateinit var logger: Logger
        lateinit var pluginConfig: PluginConfig
        var started: Boolean = false
        lateinit var server: Server
        lateinit var client: Client
    }
}