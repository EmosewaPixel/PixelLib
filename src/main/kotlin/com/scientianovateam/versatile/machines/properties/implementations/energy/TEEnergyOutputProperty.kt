package com.scientianovateam.versatile.machines.properties.implementations.energy

import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.capabilities.energy.OutputEnergyHandler

open class TEEnergyOutputProperty(value: OutputEnergyHandler, id: String, te: BaseTileEntity) : TEEnergyProperty(value, id, te) {
    constructor(capacity: Int, id: String, te: BaseTileEntity) : this(object : OutputEnergyHandler(capacity) {
        override fun onUpdate() {
            te.update()
            te.markDirty()
        }
    }, id, te)

    override fun clone(): TEEnergyOutputProperty {
        val handler = OutputEnergyHandler(value.maxEnergyStored)
        handler.energyStored = value.energyStored
        return TEEnergyOutputProperty(handler, id, te)
    }
}