package cn.windis.pluginnonebot.config

import cn.windis.pluginnonebot.PluginNonebot
import org.bukkit.configuration.file.FileConfiguration

/**
 * @author FYWinds
 */
class PluginConfig(fileConfig: FileConfiguration) {
    // TODO Load config into a Config object
    init {
        Companion.config = ConfigModel(fileConfig)
    }

    val config: ConfigModel
        get() = Companion.config

    inner class ConfigModel(fileConfig: FileConfiguration) {
        var version: String
        var isAutoStart: Boolean
        var connectionType: String
        var wsConnection: WsConfigModel? = null
        var rwsConnection: RwsConfigModel? = null
        private val _connectionConfigField: String = "connections"

        init {
            version = fileConfig.getString("version")!!
            // TODO 当 version 不匹配的时候尝试转换配置文件或者结束插件
            isAutoStart = fileConfig.getBoolean("auto-start")
            connectionType = fileConfig.getString("connection-type", "ws")!!
            try {
                when (connectionType) {
                    "ws" -> {
                        wsConnection = WsConfigModel(fileConfig.getConfigurationSection("connections.ws")!!)
                    }
                    "reverse-ws" -> {
                        rwsConnection = RwsConfigModel(fileConfig.getConfigurationSection("connections.reverse-ws")!!)
                    }
                    else -> {
                        PluginNonebot.logger.warning("配置错误: 无法识别的连接方式")
                    }
                }
            } catch (e: NullPointerException) {
                PluginNonebot.logger.warning("配置错误: 无法读取所配置的连接方式的信息")
            }

        }
    }

    companion object {
        val version = "1.0.0"
        private const val NAME = "PluginNonebot"
        private lateinit var config: ConfigModel
    }
}