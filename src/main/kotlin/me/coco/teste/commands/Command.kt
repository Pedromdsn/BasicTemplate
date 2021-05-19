package me.coco.teste.commands

import me.saiintbrisson.minecraft.command.annotation.Command
import me.saiintbrisson.minecraft.command.annotation.Optional
import me.saiintbrisson.minecraft.command.command.Context
import me.saiintbrisson.minecraft.command.target.CommandTarget
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

internal class Command {
    @Command(name = "teste", target = CommandTarget.ALL)
    fun cmd(cmd: Context<Player>, @Optional teste : Player?) {

    }
}

