package fyi.fyw.mc.pluginnonebot

import org.bukkit.plugin.java.JavaPlugin

class PluginNonebot : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()

        logger.info("${description.name} version ${description.version} Loaded!")
    }

    override fun onDisable() {
        logger.info("${description.name} version ${description.version} Unloaded!")
    }
}
