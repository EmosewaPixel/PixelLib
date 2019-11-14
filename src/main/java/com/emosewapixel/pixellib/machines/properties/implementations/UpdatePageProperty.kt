package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.ChangePagePacket
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.ReopenGUIPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.network.PacketDistributor

open class UpdatePageProperty(override val te: BaseTileEntity) : ITEBoundProperty {
    override val id = ""

    override fun detectAndSendChanges(container: BaseContainer) {
        if (container.guiPage != container.te.guiLayout.current)
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as ServerPlayerEntity }, ReopenGUIPacket(te.pos, container.type))
    }

    var pageId
        get() = te.guiLayout.pages.indexOf(te.guiLayout.current)
        set(value) {
            te.guiLayout.current = te.guiLayout.pages.getOrElse(value) { te.guiLayout.pages[0] }
            if (FMLEnvironment.dist.isClient)
                NetworkHandler.CHANNEL.sendToServer(ChangePagePacket(te.pos, value))
        }

    override fun copy() = this

    override fun deserializeNBT(nbt: CompoundNBT?) {}

    override fun serializeNBT() = nbt { }
}