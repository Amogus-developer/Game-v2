import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import functions.createWall
import kotlin.math.cos

class Entity(private val texture: Texture,
             private val body: Body,
             private val speed: Float = 50f) {

    fun renderWalls(spriteBatch: SpriteBatch){
        val wh = 5f
        val xy = 1.5f
        val x = body.position.x
        val y = body.position.y
        spriteBatch.draw(texture, x-xy, y-xy, wh, wh)
    }
    fun renderPlayer(spriteBatch: SpriteBatch){
        val wh = settings.player*2f
        val xy = (settings.player*settings.player)/wh+(settings.player/2-1)
        val x = body.position.x
        val y = body.position.y
        spriteBatch.draw(texture, x-xy, y-xy, wh, wh)
    }
    fun renderEnemy(spriteBatch: SpriteBatch){
        val wh = settings.enemy*2
        val xy = (settings.enemy*settings.enemy)/wh+(settings.enemy/2-1)
        val x = body.position.x
        val y = body.position.y
        spriteBatch.draw(texture, x-xy, y-xy, wh, wh)
    }
    fun getPosition(): Vector2 = Vector2(body.position.x + 1f, body.position.y + 1f)
    fun getBody(): Body = body
    fun applyForceToCenter(force: Vector2) {
        body.applyForceToCenter(force, true)
    }
    fun moveAt(position: Vector2) {
        val v = Vector2(position.x - body.position.x, position.y - body.position.y)
        v.nor()
        v.scl(speed)
        applyForceToCenter(v)
    }
}