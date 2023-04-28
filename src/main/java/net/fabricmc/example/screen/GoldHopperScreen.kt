package net.fabricmc.example.screen

import GoldHopperHandler
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.example.Main
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text
import net.minecraft.util.Identifier


class GoldHopperScreen(handler: GoldHopperHandler?, inventory: PlayerInventory?, title: Text?) :
    HandledScreen<GoldHopperHandler>(handler, inventory, title) {
    init {
        passEvents = false
        backgroundHeight = 133
        playerInventoryTitleY = backgroundHeight - 94
    }


    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader { GameRenderer.getPositionTexProgram() }
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)

        // Render the filter slot placeholder
        val filterSlots = handler!!.filterSlots
        for (filterSlot in filterSlots) {
            filterSlot?.run{
                if(hasStack()) drawFilterIcon(matrices, this)
            }
        }
    }

    private fun drawFilterIcon(matrices: MatrixStack, slot: Slot) {
        drawTexture(matrices, x + slot.x, y + slot.y, backgroundWidth, 0, 16, 16)
    }
}
val TEXTURE: Identifier = Main.id("textures/gui/container/golden_hopper.png")
