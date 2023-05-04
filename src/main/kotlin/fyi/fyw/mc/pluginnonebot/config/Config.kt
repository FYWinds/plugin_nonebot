package fyi.fyw.mc.pluginnonebot.config

import fyi.fyw.mc.pluginnonebot.config.connection.WsConfig
import org.bukkit.configuration.file.YamlConfiguration

class Config(config: YamlConfiguration) {
    var serverName: String
    var heartbeatInterval: Int
    var message: MessageConfig
    var output: OutputConfig
    var api: ApiConfig
    var wsConfigs: MutableList<WsConfig> = mutableListOf()

    init {
        serverName = config.getString("server-name", "Minecraft Server")!!
        heartbeatInterval = config.getInt("heartbeat.interval", 0)
        message = MessageConfig(config)
        output = OutputConfig(config)
        api = ApiConfig(config)
        config.getList("connections")!!.forEach {
            @Suppress("UNCHECKED_CAST")
            wsConfigs.add(WsConfig(it as Map<String, Map<String, *>>).from(it))
        }
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: Config
    }
}
