package net.fabricmc.example.block

import GoldHopperHandler
import net.fabricmc.example.Main
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.HopperBlockEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.ItemScatterer.spawn
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import java.util.stream.IntStream

class GoldHopperEntity (pos: BlockPos?, state: BlockState?) : HopperBlockEntity(pos, state), SidedInventory{
    override fun getContainerName() = Text.translatable("container.golden_hopper")
    override fun getType() = Main.GOLD_HOPPER_BLOCK_ENTITY_TYPE
    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory) = GoldHopperHandler(syncId, playerInventory, filterInventory, this)

    private val filterInventory: Inventory = SimpleInventory(5)
    override fun getAvailableSlots(side: Direction?) = IntStream.range(41,46).toArray()

    override fun canInsert(slot: Int, stack: ItemStack?, direction: Direction?) = true
    override fun canExtract(slot: Int, stack: ItemStack?, direction: Direction?) = true

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)
        filterInventory.setStack(0, ItemStack.fromNbt(tag.getCompound("Filter")))
    }

    override fun writeNbt(tag: NbtCompound) {
        tag.put("Filter", filterInventory.getStack(0).writeNbt(NbtCompound()))
        super.writeNbt(tag)
    }
}