package com.scientianova.versatile.machines.properties.implementations.items

import com.scientianova.versatile.common.extensions.get
import com.scientianova.versatile.common.extensions.set
import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.capabilities.items.InputItemStackHandler

open class TEItemInputProperty(override val value: InputItemStackHandler, id: String, te: BaseTileEntity) : TEItemInventoryProperty(value, id, te) {
    constructor(slots: Int, id: String, te: BaseTileEntity) : this(object : InputItemStackHandler(slots) {
        override fun onContentsChanged(slot: Int) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone(): TEItemInputProperty {
        val stackHandler = InputItemStackHandler(value.slots)
        (0 until value.slots).forEach { stackHandler[it] = value[it].copy() }
        return TEItemInputProperty(stackHandler, id, te)
    }
}