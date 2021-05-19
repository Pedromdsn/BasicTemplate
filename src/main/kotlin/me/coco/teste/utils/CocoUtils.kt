package me.coco.teste.utils

import com.google.common.base.Strings
import com.google.gson.Gson
import com.google.gson.JsonElement
import me.coco.teste.instance
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.*
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.*
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.util.*


/*By Coco Blanco#3873*/

/*Basic*/

internal fun String.f(): String = ChatColor.translateAlternateColorCodes('&', this)

internal fun item(material: Material, nome: String, lore: String): ItemStack {
    val item = ItemStack(material)
    val meta = item.itemMeta
    meta.displayName = nome.f()
    meta.lore = lore.split('\n').map { it.f() }
    item.itemMeta = meta
    return item
}

internal fun item(material: Material, nome: String): ItemStack {
    val item = ItemStack(material)
    val meta = item.itemMeta
    meta.displayName = nome.f()
    item.itemMeta = meta
    return item
}

internal fun createTextoClicavel(p: Player, a: ClickEvent.Action, acao: String, texto: String) {
    val texto1 = TextComponent(texto.f())
    texto1.clickEvent = ClickEvent(a, acao)
    p.spigot().sendMessage(texto1)
}

internal fun createTextoHover(p: Player, a: HoverEvent.Action, texto: String, texto2: String) {
    val texto1 = TextComponent(texto.f())
    texto1.hoverEvent = HoverEvent(a, ComponentBuilder(texto2.f()).create())
    p.spigot().sendMessage(texto1)
}

internal fun progressBar(
    current: Int,
    max: Int,
    totalBars: Int,
    symbol: String,
    color0: String,
    color1: String
): String {
    val percent = current.toFloat() / max
    val progressBars = (totalBars * percent).toInt()
    return color0 + Strings.repeat(symbol.f(), progressBars)
        .toString() + color1 + Strings.repeat(symbol.f(), totalBars - progressBars)
}

internal fun Location.str() = "${block.world.name}/${blockX}/${blockY}/${blockZ}"

internal fun String.loc(): Location {
    val texto = split("/")
    return Location(Bukkit.getWorld(texto[0]), texto[1].toDouble(), texto[2].toDouble(), texto[3].toDouble())
}

internal fun recipe(item: ItemStack, vararg rows: String): ShapedRecipe =
    ShapedRecipe(item).shape(rows[0], rows[1], rows[2])

internal fun ShapedRecipe.item(key: Char, material: Material): ShapedRecipe = setIngredient(key, material)

internal fun ShapedRecipe.build() = instance().server.addRecipe(this)

internal fun chance(percentagem: Int) = Math.random() * 100 < percentagem

internal fun criarNPC(loc: Location, texto: String): ArmorStand {
    val npc = loc.world.spawnEntity(loc, EntityType.ARMOR_STAND) as ArmorStand
    npc.customName = texto
    npc.isCustomNameVisible = true
    npc.isSmall = true
    npc.setGravity(false)
    npc.isVisible = false
    npc.canPickupItems = false
    return npc
}

internal fun tempoform(tempo: Int) = if (tempo < 10) "0$tempo" else "$tempo"

internal fun tempo(tempo: Int) = "${tempoform(tempo / 60)}:${tempoform(tempo % 60)}"

internal fun head(skin: String): ItemStack {
    val meta = Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM) as SkullMeta
    val skull = ItemStack(Material.SKULL_ITEM, 1, 1, 3.toByte())
    meta.owner = skin
    skull.itemMeta = meta
    return skull
}

/*Inventory*/
internal fun Inventory.occupiedSlots() = contents.count { it?.type != Material.AIR }

internal fun Inventory.freeSlot() = contents.count { it == null || it.type == Material.AIR }

internal fun Inventory.full() = firstEmpty() == -1

internal fun PlayerInventory.clean() = occupiedSlots() == 0 && armorContents.count { it?.type != Material.AIR } == 0

internal fun Inventory.clean() = occupiedSlots() == 0

internal fun Inventory.space(slots: Int) = freeSlot() >= slots

internal fun Inventory.space(item: ItemStack): Boolean {
    var emptySpace = 0
    contents
        .filter { it == item }
        .forEach { emptySpace += it.amount - it.maxStackSize }
    return freeSlot() * item.maxStackSize + emptySpace < item.amount
}

internal fun Inventory.amountItem(item: ItemStack): Int {
    var amount = 0
    contents
        .filter { it == item }
        .forEach { amount += it.amount }
    return amount
}

/*Item*/
internal fun ItemStack.amount(amount: Int): ItemStack {
    setAmount(amount)
    return this
}

internal fun ItemStack.name(name: String): ItemStack {
    val meta = itemMeta
    meta.displayName = name.f()
    itemMeta = meta
    return this
}

internal fun ItemStack.lore(text: String): ItemStack {
    val meta = itemMeta
    meta.lore = text.split("\n").map { it.f() }
    itemMeta = meta
    return this
}

internal fun ItemStack.lore(vararg text:String): ItemStack {
    val meta = itemMeta
    meta.lore = text.map { it.f() }
    itemMeta = meta
    return this

}

internal fun ItemStack.durability(durability: Int): ItemStack {
    setDurability(durability.toShort())
    return this
}

internal fun ItemStack.clearLore(): ItemStack {
    val meta = itemMeta
    meta.lore = ArrayList()
    itemMeta = meta
    return this
}

internal fun ItemStack.clearEnchantments(): ItemStack {
    enchantments.keys.forEach { removeEnchantment(it) }
    return this
}

internal fun ItemStack.enchantment(enchant: Enchantment, nivel: Int = 1): ItemStack {
    this.addUnsafeEnchantment(enchant, nivel)
    return this
}

internal fun ItemStack.color(color: Color): ItemStack {
    if (type.name.startsWith("LEATHER")) {
        val meta = itemMeta as LeatherArmorMeta
        meta.color = color
        itemMeta = meta
        return this
    } else {
        throw IllegalArgumentException("Colors only applicable for leather armor!")
    }
}

internal fun ItemStack.flag(flag: ItemFlag): ItemStack {
    val meta = itemMeta
    meta.addItemFlags(flag)
    itemMeta = meta
    return this
}


//YAML
internal fun JavaPlugin.saveYaml(fileName: String, saveDefault: Boolean = false) {
    if (!dataFolder.exists()) dataFolder.mkdir()
    val file = File(dataFolder, "$fileName.yml")
    if (saveDefault) saveResource("$fileName.yml", false)
    if (!file.exists()) {
        try {
            file.parentFile.mkdir()
            file.createNewFile()
        } catch (e: IOException) {
            logger.warning("Não consegui criar o ficheiro $fileName")
        }
    }
}

internal fun config(fileName: String): FileConfiguration =
    YamlConfiguration.loadConfiguration(File(instance().dataFolder, "$fileName.yml"))

//JSON
internal fun Any.toJson() = Gson().toJson(this)

internal fun String.jsonToObject(type : Class<*>) = Gson().fromJson(this, type)
internal fun JsonElement.toObject(type : Class<*>) = Gson().fromJson(this, type)