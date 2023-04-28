package net.fabricmc.example;

import GoldHopperHandler
import net.fabricmc.api.ModInitializer
import net.fabricmc.example.block.GoldHopperBlock
import net.fabricmc.example.block.GoldHopperEntity
import net.fabricmc.example.screen.GoldHopperScreen
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.MapColor
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.resource.featuretoggle.FeatureFlags
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

object Main : ModInitializer {
	const val MOD_ID = "modid"
	val GOLD_HOPPER_ID = id("golden_hopper")

	val GOLD_HOPPER_BLOCK = GoldHopperBlock(FabricBlockSettings.copyOf(Blocks.HOPPER).mapColor(MapColor.GOLD))
	val GOLD_HOPPER = BlockItem(GOLD_HOPPER_BLOCK, FabricItemSettings())
	var GOLD_HOPPER_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(
		{ pos: BlockPos?, state: BlockState? -> GoldHopperEntity(pos, state) }, GOLD_HOPPER_BLOCK).build()
	var GOLD_SCREEN_HANDLER_TYPE = ScreenHandlerType(
		{ i: Int, inv: PlayerInventory -> GoldHopperHandler(i, inv)}
		, FeatureFlags.VANILLA_FEATURES)

	override fun onInitialize() {
		regBlock(GOLD_HOPPER_ID, GOLD_HOPPER_BLOCK)
		regItem(GOLD_HOPPER_ID, GOLD_HOPPER)
		regBlockEntity<BlockEntityType<GoldHopperEntity>>(GOLD_HOPPER_ID, GOLD_HOPPER_BLOCK_ENTITY_TYPE)
		regScreenHandler(GOLD_HOPPER_ID, GOLD_SCREEN_HANDLER_TYPE)
		HandledScreens.register(GOLD_SCREEN_HANDLER_TYPE) {
			handler: GoldHopperHandler, inv: PlayerInventory, title: Text
			-> GoldHopperScreen(handler, inv, title)
		}

	}

	fun id(key: String?): Identifier = Identifier(MOD_ID, key)
	private fun <V, T : V?> register(registry: Registry<V>, id: Identifier, entry: T) = Registry.register(registry, id, entry)
	private fun <T : Block?> regBlock(id: Identifier, entry: T) = register(Registries.BLOCK, id, entry)
	private fun <T : Item?> regItem(id: Identifier, entry: T) = register(Registries.ITEM, id, entry)
	private fun <T : ScreenHandlerType<*>?> regScreenHandler(id: Identifier, entry: T) = register(Registries.SCREEN_HANDLER, id, entry)
	private fun <T : BlockEntityType<*>?> regBlockEntity(id: Identifier, entry: T) = register(Registries.BLOCK_ENTITY_TYPE, id, entry)
}