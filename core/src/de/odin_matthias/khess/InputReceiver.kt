package de.odin_matthias.khess

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import de.odin_matthias.khess.systems.AttackSystem
import de.odin_matthias.khess.systems.CastlingSystem
import de.odin_matthias.khess.systems.MovementSystem
import de.odin_matthias.khess.systems.PieceSelectSystem


class InputReceiver(val engine: Engine) : InputProcessor {

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (button == Input.Buttons.LEFT) {
            val selectedAPiece = engine.getSystem(PieceSelectSystem::class.java).select()
        } else if (button == Input.Buttons.RIGHT) {
            var acted = engine.getSystem(MovementSystem::class.java).move()
            if (!acted) acted = engine.getSystem(AttackSystem::class.java).attack()
            if (!acted) acted = engine.getSystem(CastlingSystem::class.java).castle()
        }


        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        return true
    }


    override fun keyDown(keycode: Int): Boolean {
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }
}