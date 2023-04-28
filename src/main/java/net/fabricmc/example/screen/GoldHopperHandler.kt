import net.fabricmc.example.Main
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

class GoldHopperHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    val filterInventory: Inventory,
    val inventory: Inventory
) : ScreenHandler(Main.GOLD_SCREEN_HANDLER_TYPE, syncId) {
    constructor(syncId: Int, playerInventory: PlayerInventory)
            : this(syncId, playerInventory, SimpleInventory(5), SimpleInventory(5))

    val filterSlots = arrayOfNulls<Slot>(5)

    init {
        // Hotbar
        for (column in 0..8)
            addSlot(Slot(playerInventory, column, column * 18 + 8, 109))
        // Player inventory
        for (row in 0..2)
            for (column in 0..8)
                addSlot(Slot(playerInventory, column + row * 9 + 9, column * 18 + 8, row * 18 + 51))
        // Filter
        checkSize(filterInventory, 5)
        for (i in filterSlots.indices) {
            println("filter$i")
            filterSlots[i] =
                addSlot(object : Slot(this.filterInventory, i, i * 18 + 62, 40) {
                    override fun getMaxItemCount() = 1
                })
        }
        filterInventory.onOpen(playerInventory.player)
        //  Hopper inventory
        checkSize(inventory, 5)
        for (i in 0 until inventory.size()) {
            println("hopp $i")
            addSlot(Slot(this.inventory, i, i * 18 + 62, 20))
        }
        println("onopen")
        inventory.onOpen(playerInventory.player)
        println("onopen done")
    }

    override fun quickMove(player: PlayerEntity?, index: Int) = ItemStack.EMPTY
    override fun onClosed(player: PlayerEntity?) {
        super.onClosed(player)
        inventory.onClose(player)
        filterInventory.onClose(player)
    }

    override fun canUse(player: PlayerEntity) = filterInventory.canPlayerUse(player)
}