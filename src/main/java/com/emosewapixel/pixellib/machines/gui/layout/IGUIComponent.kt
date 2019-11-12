package com.emosewapixel.pixellib.machines.gui.layout

import com.emosewapixel.pixellib.machines.gui.GUiUtils
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

interface IGUIComponent {
    val x: Int
    val y: Int
    val width: Int
    val height: Int
    val tooltips: MutableList<String>

    @OnlyIn(Dist.CLIENT)
    fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun onMouseDragged(mouseX: Double, mouseY: Double, clickType: Int): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun onMouseReleased(mouseX: Double, mouseY: Double, clickType: Int): Boolean = false

    @OnlyIn(Dist.CLIENT)
    fun drawInBackground(mouseX: Int, mouseY: Int, xOffset: Int, yOffset: Int)

    @OnlyIn(Dist.CLIENT)
    fun isSelected(mouseX: Int, mouseY: Int): Boolean

    @OnlyIn(Dist.CLIENT)
    fun drawInForeground(mouseX: Int, mouseY: Int, xOffset: Int, yOffset: Int) {
        if (tooltips.isNotEmpty() && isSelected(mouseX - xOffset, mouseY - yOffset))
            GUiUtils.renderTooltip(tooltips, mouseX, mouseY)
    }
}