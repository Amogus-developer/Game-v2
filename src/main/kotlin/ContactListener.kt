import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

class ContactListener: ContactListener{
    override fun beginContact(contact: Contact) {
        val game = Game()
        val fixA = contact.fixtureA
        val fixB = contact.fixtureB
        //println(fixA.userData.toString() + " has hit "+ fixB.userData.toString())
        if (fixA.userData == 20 && fixB.userData == 10){ return Gdx.app.exit() }
        if (fixA.userData == 10 && fixB.userData == 20){ return Gdx.app.exit() }

        if (fixA.userData == 30 && fixB.userData == 20){ return println("hit") }
        if (fixA.userData == 20 && fixB.userData == 30){ return println("hit") }

    }
    override fun endContact(contact: Contact) {}
    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}
}