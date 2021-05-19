package me.coco.teste

import me.bristermitten.pdm.PluginDependencyManager
import me.coco.teste.commands.Command
import me.coco.teste.events.Event
import me.coco.teste.menu.Menu
import me.coco.teste.utils.f
import me.saiintbrisson.bukkit.command.BukkitFrame
import me.saiintbrisson.bukkit.command.executor.BukkitSchedulerExecutor
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

private const val useConfig = false

private fun BukkitFrame.commands() {
    registerCommands(Command())
}

private fun events() {
    Bukkit.getPluginManager().registerEvents(Event(), instance())
    Bukkit.getPluginManager().registerEvents(Menu(), instance())
}

private fun onStart() {

}

private fun onStop() {

}























class Main : JavaPlugin() {

    override fun onEnable() {
        PluginDependencyManager.of(this).loadAllDependencies().exceptionally {
            it.printStackTrace()
            logger.severe("Ocorreu um erro a iniciar o Plugin")
            Bukkit.getPluginManager().disablePlugin(this)
            return@exceptionally null
        }.thenRun {
            if (useConfig && instance().config != null) config()
            events()
            val frame = BukkitFrame(this)
            frame.commands()
            frame.executor = BukkitSchedulerExecutor(this)
            onStart()
            Bukkit.getConsoleSender().sendMessage("&aPlugin iniciado com sucesso".f())
            super.onEnable()
        }
    }

    override fun onDisable() {
        onStop()
        Bukkit.getConsoleSender().sendMessage("&cPlugin Desligado".f())
        super.onDisable()
    }

    private fun config() {
        config.options().copyDefaults(true)
        saveConfig()
        reloadConfig()
    }
}

internal fun instance(): Main = JavaPlugin.getPlugin(Main::class.java)
internal fun config(): FileConfiguration = instance().config