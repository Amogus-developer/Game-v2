package functions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2

class ForceController(private val force: Float = 95F): InputAdapter() {
    private val bodyForce = Vector2()
    override fun keyDown(keycode: Int): Boolean {
        bodyForce.apply {
            when (keycode) {
                A -> x -= force
                D -> x += force
                W -> y += force
                S -> y -= force
            }
        }
        when (keycode) {
            ESCAPE -> Gdx.app.exit()
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        bodyForce.apply {
            when (keycode) {
                A -> x += force
                D -> x -= force
                W -> y -= force
                S -> y += force
            }
        }
        return false
    }
    fun getForce() = bodyForce
}