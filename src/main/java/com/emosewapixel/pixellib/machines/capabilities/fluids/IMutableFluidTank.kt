package com.emosewapixel.pixellib.machines.capabilities.fluids

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidTank

interface IMutableFluidTank : IFluidTank {
    fun setFluid(stack: FluidStack)
}