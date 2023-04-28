package net.fabricmc.example.block

import net.fabricmc.example.Main
import net.minecraft.block.BlockState
import net.minecraft.block.HopperBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.HopperBlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class GoldHopperBlock(settings: Settings) : HopperBlock(settings) {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?) = GoldHopperEntity(pos, state)
    override fun <T : BlockEntity?> getTicker(world: World, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        if (world.isClient) return null
        return checkType(type, Main.GOLD_HOPPER_BLOCK_ENTITY_TYPE, HopperBlockEntity::serverTick)
    }
}