package de.odin_matthias.khess.extensions

import com.badlogic.gdx.math.Vector3


operator fun Vector3.component1() = x
operator fun Vector3.component2() = y
operator fun Vector3.component3() = z