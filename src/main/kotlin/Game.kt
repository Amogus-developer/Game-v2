import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.ObjectMap
import functions.*
import map.createMap
import org.lwjgl.opengl.AMDSamplePositions
import org.lwjgl.opengl.GL20
import java.io.File
import javax.imageio.ImageIO

class Game: Game() {
    private lateinit var world: World
    private lateinit var camera: OrthographicCamera
    private lateinit var player: Entity

    private val enemies = ArrayList<Entity>()
    private val bodyWalls = ArrayList<Entity>()
    private val clearWalls = ArrayList<Vector2>()
    private val textureList = ObjectMap<Int, Texture>()
    private val musicList = ObjectMap<String, Sound>()

    private val blocks: ArrayList<Body> = ArrayList()
    private var bullets: ArrayList<Body> = ArrayList()

    private lateinit var forceController: ForceController
    private lateinit var spriteBatch: SpriteBatch

    override fun create() {
        Gdx.gl.glClearColor(0.18f, 0.18f, 0.18f, 1f)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        world = World(Vector2(0f, 0f), true)
        world.setContactListener(ContactListener())

        spriteBatch = SpriteBatch()

        camera = OrthographicCamera(50f, 25f)
        camera.position.set(Vector2(0f, 0f), 0f)


        textureList.put(Textures.PLAYER, Texture("icons/player.png"))
        textureList.put(Textures.ENEMY, Texture("icons/enemy.png"))
        textureList.put(Textures.BODY_WALL, Texture("icons/block.jpg"))
        textureList.put(Textures.CLEAR_WALL, Texture("icons/grass.jpg"))

        textureList.put(Textures.BULLET, Texture("icons/bullet.png"))

        musicList.put(Music.GOD, Gdx.audio.newSound(Gdx.files.internal("music/sus.mp3")))

        loadMap("map.png")
        musicList[Music.GOD].play(0.6f)
        forceController = ForceController(player, blocks, bullets, world)
        Gdx.input.inputProcessor = forceController

    }
    private fun loadMap(path: String){

        val p = textureList[Textures.PLAYER]
        val en = textureList[Textures.ENEMY]
        val bw = textureList[Textures.BODY_WALL]

        createMap(ImageIO.read(File(path)), 5F, 5F).forEach {
            when(it.color) {
                Textures.PLAYER -> player = Entity(p, world.createCirclePlayer(it.x, it.y, 2f))
                Textures.BODY_WALL -> bodyWalls.add(Entity(bw, world.createWall(it.x, it.y, 2.5f, 2.5f)))
                Textures.CLEAR_WALL -> clearWalls.add(Vector2(it.x, it.y))
                Textures.ENEMY -> enemies.add(Entity(en, world.createCircleEnemy(it.x, it.y, 1.5f), speed = 60f))
            }
            when(it.color) {
                Textures.PLAYER -> clearWalls.add(Vector2(it.x, it.y))
                Textures.ENEMY -> clearWalls.add(Vector2(it.x, it.y))
            }
        }
    }
    override fun render() {
        update()
        spriteBatch.begin()

        clearWalls.forEach {spriteBatch.draw(textureList[Textures.CLEAR_WALL], it.x-1.5f, it.y-1.5f, 5f, 5f)}
        bodyWalls.forEach { it.renderWalls(spriteBatch) }
        enemies.forEach { it.renderEnemy(spriteBatch) }
        player.renderPlayer(spriteBatch)

        blocks.forEach { spriteBatch.draw(textureList[Textures.BODY_WALL], it.position.x-1.5f, it.position.y-1.5f, 5f, 5f)}
        bullets.forEach { spriteBatch.draw(textureList[Textures.BULLET], it.position.x, it.position.y, 1.5f, 1.5f)}

        spriteBatch.end()
    }
    private fun update(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        world.step(1/60f, 4, 4) //Раз за секунду просчет координат тел
        camera.position.set(player.getPosition(), 0f)
        camera.update()
        spriteBatch.projectionMatrix = camera.combined
        player.applyForceToCenter(forceController.getForce())
        enemies.forEach { it.moveAt(player.getPosition()) }
    }
    fun addBlock(block: ArrayList<Body>, position: Vector2,wor: World){
        block.add(wor.createWall(position.x, position.y, 2.5f,2.5f))
        block.forEach { blocks.add(it) }
    }
    fun addBullet(bullet: ArrayList<Body>, position: Vector2, wor: World, direction: Vector2){
        val body = wor.createCircleBullet(position.x, position.y, 0.5f)
        body.applyForceToCenter(direction, false)
        bullet.add(body)
        bullet.forEach {
            bullets.add(it)
        }
    }
    override fun resize(width: Int, height: Int) {
        val size = 12.5f
        val camWidth: Float = size * size
        val camHeight = camWidth * (height.toFloat() / width.toFloat())
        camera.viewportWidth = camWidth
        camera.viewportHeight = camHeight
        camera.update()
    }
    override fun dispose() {
        world.dispose()
        textureList.values().forEach { it.dispose() }
        musicList.values().forEach { it.dispose() }
    }
}