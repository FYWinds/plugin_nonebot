package cn.windis.pluginnonebot

import cn.windis.pluginnonebot.commands.MainCommand
import cn.windis.pluginnonebot.commands.TabHandler
import cn.windis.pluginnonebot.config.PluginConfig
import cn.windis.pluginnonebot.event.MainEventListener
import cn.windis.pluginnonebot.utils.Logger
import cn.windis.pluginnonebot.webSocket.IWsConnection
import cn.windis.pluginnonebot.webSocket.client.Client
import cn.windis.pluginnonebot.webSocket.server.Server
import cn.windis.pluginnonebot.webSocket.server.Socket
import io.ktor.websocket.*
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author FYWinds
 */
class PluginNonebot : JavaPlugin() {

    override fun onEnable() {

        this.saveDefaultConfig()

        instance = this
        Companion.logger = Logger(this.logger)
        pluginConfig = PluginConfig(this.config)
        if (pluginConfig.config.isAutoStart) {
            connect()
        }
        Bukkit.getPluginCommand("pluginnonebot")?.setExecutor(MainCommand())
        Bukkit.getPluginCommand("pluginnonebot")?.tabCompleter = TabHandler()
        Bukkit.getPluginManager().registerEvents(MainEventListener(), this)
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
                Socket.disconnect(CloseReason(CloseReason.Codes.NORMAL, "Plugin stopped"))
            }
            "reverse-ws" -> {
                Client.disconnect(CloseReason(CloseReason.Codes.NORMAL, "Plugin stopped"))
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
        const val PREFIX_PN = "§f[§aPlugin§cNonebot§f] "

        fun getWsConnection(): IWsConnection {
            return when (pluginConfig.config.connectionType) {
                "ws" -> {
                    Socket.Companion
                }
                "reverse-ws" -> {
                    Client.Companion
                }
                else -> {
                    throw IllegalArgumentException("Unknown connection type")
                }
            }
        }

        fun getToken(): String {
            return when (pluginConfig.config.connectionType) {
                "ws" -> {
                    pluginConfig.config.wsConnection!!.token
                }
                "reverse-ws" -> {
                    pluginConfig.config.rwsConnection!!.token
                }
                else -> {
                    throw IllegalArgumentException("Unknown connection type")
                }
            }
        }
    }
}