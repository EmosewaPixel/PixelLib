package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateSlotPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.network.NetworkDirection

open class TEItemInventoryProperty(value: ImprovedItemStackHandler, override val id: String, override val te: BaseTileEntity) : ItemInventoryProperty(value), ITEBoundProperty {
    constructor(te: BaseTileEntity, id: String, slots: Int, noOutput: IntRange, noInput: IntRange) : this(
            object : ImprovedItemStackHandler(slots, noOutput, noInput) {
                override fun onContentsChanged(slot: Int) = te.update()
            }, id, te
    )

    constructor(te: BaseTileEntity, id: String, inputCount: Int, outputCount: Int) : this(
            te, id, inputCount + outputCount, 0 until inputCount, inputCount until inputCount + outputCount
    )

    override fun detectAndSendChanges(container: BaseContainer) {
        if (container.te.world?.isRemote == true) return
        val clientHandler = (container.clientProperties[id] as TEItemInventoryProperty).value
        clientHandler.invStacks.indices.forEach { index ->
            if (!clientHandler.invStacks[index].equals(value.invStacks[index], false)) {
                NetworkHandler.CHANNEL.sendTo(UpdateSlotPacket(id, index, value.invStacks[index]), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
                clientHandler.invStacks[index] = value.invStacks[index].copy()
            }
        }
    }

    override fun copy() = TEItemInventoryProperty(value.copy(), id, te)

    override fun deserializeNBT(nbt: CompoundNBT?) {
        if (nbt?.contains(id) == true) value.deserializeNBT(nbt.getCompound(id))
    }

    override fun serializeNBT() = nbt {
        id to value
    }
}