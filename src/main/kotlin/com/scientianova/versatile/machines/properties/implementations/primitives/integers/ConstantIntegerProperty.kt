package com.scientianova.versatile.machines.properties.implementations.primitives.integers

import com.scientianova.versatile.machines.properties.IValueProperty

open class ConstantIntegerProperty(override val value: Int) : IValueProperty<Int> {
    override fun clone() = ConstantIntegerProperty(value)
}