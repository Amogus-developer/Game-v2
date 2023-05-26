import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun main(args: Array<String>) {

    val config = Lwjgl3ApplicationConfiguration()

    config.setForegroundFPS(2 * 60)
    config.setIdleFPS(30)
    config.useVsync(false)
    config.setTitle("GB")
    config.setResizable(true)
    config.setWindowedMode(1600,900)
    config.setWindowIcon(Files.FileType.Classpath, "icons/icon.jpg")

    Lwjgl3Application(Game(), config)
}