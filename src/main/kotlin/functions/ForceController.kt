package functions

import Entity
import Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import settings

class ForceController(private val player: Entity,
                      private val blocks: ArrayList<Body>,
                      private val bullets: ArrayList<Body>,
                      private val world: World,
                      private val force: Float = settings.playerSpeed,
                      private val speed: Float = 50000f): InputAdapter() {

    private val bodyForce = Vector2()
    private val game = Game()

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
            NUMPAD_4 -> game.addBlock(blocks, Vector2(player.getPosition().x-6f, player.getPosition().y-1f), world)
            NUMPAD_6 -> game.addBlock(blocks, Vector2(player.getPosition().x+4f, player.getPosition().y-1f), world)
            NUMPAD_8 -> game.addBlock(blocks, Vector2(player.getPosition().x-1f, player.getPosition().y+4f), world)
            NUMPAD_5 -> game.addBlock(blocks, Vector2(player.getPosition().x-1f, player.getPosition().y-6f), world)

            LEFT -> game.addBullet(bullets, Vector2(player.getPosition().x-5.5f, player.getPosition().y-1f), world, Vector2(-speed, 0f))
            RIGHT -> game.addBullet(bullets, Vector2(player.getPosition().x+4f, player.getPosition().y-1f), world, Vector2(speed, 0f))
            UP -> game.addBullet(bullets, Vector2(player.getPosition().x-0.7f, player.getPosition().y+4f), world, Vector2(0f, speed))
            DOWN -> game.addBullet(bullets, Vector2(player.getPosition().x-0.7f, player.getPosition().y-5.5f), world, Vector2(0f, -speed))

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