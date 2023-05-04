package fyi.fyw.mc.pluginnonebot.config

import org.bukkit.configuration.file.YamlConfiguration
import java.util.logging.Level

class OutputConfig(config: YamlConfiguration) {
    var level: Level = Level.INFO
    var channel: Channel = Channel.CONSOLE
    var file: String = "logs/"

    init {
        level = LogLevel.fromText(config.getString("output.level", "INFO")!!).level
        channel = Channel.valueOf(
            config.getString("output.channel", "CONSOLE")!!.uppercase(),
        )
        file = config.getString("output.file", "logs/")!!
    }

    enum class LogLevel(val level: Level) {
        TRACE(Level.FINEST),
        DEBUG(Level.FINER),
        INFO(Level.INFO),
        WARN(Level.WARNING),
        ERROR(Level.SEVERE), ;

        companion object {
            fun fromText(text: String): LogLevel {
                return LogLevel.valueOf(text.uppercase())
            }

            fun fromJavaLevel(level: Level): LogLevel {
                return LogLevel.values().find { it.level == level }!!
            }
        }
    }

    enum class Channel {
        CONSOLE,
        FILE,
    }
}
