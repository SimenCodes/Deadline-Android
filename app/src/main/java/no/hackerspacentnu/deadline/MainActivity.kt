package no.hackerspacentnu.deadline

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hoursLeftTextView.text = "X hours left" // Now we update the text!
    }

    /*
     * This code is run when the “Change deadline” button is clicked.
     * We set the onClick listener attribute of the button in `activity_main.xml` to make it happen.
     */
    fun onChangeDeadlineClicked(viewThatWasClicked: View) {
        val hoursLeft = Math.round(Math.random() * 100)
        hoursLeftTextView.text = "$hoursLeft hours left"
    }
}
