package fyi.fyw.mc.pluginnonebot

import com.google.gson.Gson
import fyi.fyw.mc.pluginnonebot.api.ApiHandlerRegistry
import fyi.fyw.mc.pluginnonebot.config.Config
import fyi.fyw.mc.pluginnonebot.config.connection.WsClientConfig
import fyi.fyw.mc.pluginnonebot.config.connection.WsConfig
import fyi.fyw.mc.pluginnonebot.config.connection.WsServerConfig
import fyi.fyw.mc.pluginnonebot.events.EventBroadcasterRegistry
import fyi.fyw.mc.pluginnonebot.websockets.NWebSocketClient
import fyi.fyw.mc.pluginnonebot.websockets.NWebSocketServer
import fyi.fyw.mc.pluginnonebot.websockets.Websockets
import fyi.fyw.mc.pluginnonebot.websockets.WebsocketsRegistry
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class PluginNonebot : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        pluginLogger = logger
        try {
            loadedConfig = Config(this.config as YamlConfiguration)
        } catch (e: Exception) {
            logger.severe("Error loading config: ${e.message}")
            logger.severe("Please check the config file")
            logger.severe("Disabling plugin...")
            server.pluginManager.disablePlugin(this)
            return
        }
        ApiHandlerRegistry.init()
        EventBroadcasterRegistry.init()
        EventBroadcasterRegistry.load(this)
        logger.info("${description.name} version ${description.version} Loaded!")

        // TODO: start websocket connections
        loadedConfig.wsConfigs.forEach { conn ->
            val connection = buildConnection(conn)
            connection.start()
        }
    }

    override fun onDisable() {
        // TODO: stop websocket connections
        WebsocketsRegistry.values.values.forEach {
            it.stop()
        }
        logger.info("${description.name} version ${description.version} Unloaded!")
    }

    private fun buildConnection(conn: WsConfig): Websockets {
        return when (conn) {
            is WsClientConfig -> NWebSocketClient(conn)
            is WsServerConfig -> NWebSocketServer(conn)
            else -> throw IllegalArgumentException("This should never happen")
        }
    }

    companion object {
        lateinit var INSTANCE: PluginNonebot
        lateinit var loadedConfig: Config
        lateinit var pluginLogger: Logger
        val gson: Gson = Gson()
    }
}
