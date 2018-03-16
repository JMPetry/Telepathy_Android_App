package codes.jean.telepathy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_app__main.*

/**
 * Connected with the first activity the user sees, just transfers the user to the next activity
 */
class AppMain : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide top bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_app__main)

        //switches to Connect when the button is pressed
        bSearch.setOnClickListener{
            val switchToMenu = Intent(this, Connect::class.java)
            startActivity(switchToMenu)
        }
    }
}
