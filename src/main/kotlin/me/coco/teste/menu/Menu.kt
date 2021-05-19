package me.coco.teste.menu

import me.coco.teste.utils.f
import me.coco.teste.utils.name
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

private val menuNome = "Teste".f()
private val itemNome = "Teste".f()

fun createMenu(): Inventory {
    val menu = Bukkit.createInventory(null, 3 * 9, menuNome)
    menu.setItem(13, ItemStack(Material.DIRT).name(itemNome))

    return menu
}

class Menu : Listener {
    @EventHandler
    fun menu(e: InventoryClickEvent) {
        if (e.view?.topInventory?.name != menuNome) return
        e.isCancelled = true
        val p = e.whoClicked as Player
        when (e.currentItem.itemMeta.displayName) {
            itemNome -> {

            }
            itemNome -> {

            }
        }
    }
}