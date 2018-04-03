package no.hackerspacentnu.deadline

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION") // Don't complain about Date
class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    val deadline = Date() // Please don't use `Date` in real apps.

/*
    // Alternative "property-style" approach
    val hoursLeft: Long
        get() {
            val now = Date()
            val diff = deadline.time - now.time // Number of milliseconds from now until the deadline
            return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) // Convert ms to hours
        }
*/

    // Lazy load to prevent using `applicationContext` before `onCreate`
    val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the deadline from file
        deadline.time = prefs.getLong("deadline", System.currentTimeMillis())

        // Move the grim reaper offscreen before the app is displayed
        reaperImageView.translationX = -1000f
    }

    /*
     * This code is run whenever the app is about to become visible on the screen somehow
     */
    override fun onStart() {
        super.onStart() // DO NOT DELETE THIS LINE
        updateUI() // Ensure UI is up-to-date
    }

    /*
     * This code is run when the “Change deadline” button is clicked.
     * We set the onClick listener attribute of the button in `activity_main.xml` to make it happen.
     */
    fun onChangeDeadlineClicked(viewThatWasClicked: View) {
        val pickerDialog = DatePickerDialog(
                this, // Give dialog some context about where it is being created. An activity is a valid context.
                this, // Tell dialog responses should be delivered right here (more precisely, in onDateSet).
                deadline.year + 1900, // When dialog opens, pre-select current deadline
                deadline.month,
                deadline.date
        )
        pickerDialog.show() // Show picker
    }

    /*
     * This code is run when the user taps "OK" in the date picker.
     */
    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        // Update our deadline with the selected date
        deadline.year = year - 1900
        deadline.month = month
        deadline.date = dayOfMonth
        // Assume all deadlines are right before midnight.
        // Use TimePickerDialog if you want a proper solution.
        deadline.hours = 23
        deadline.minutes = 50

        // Ensure number of hours left is updated
        updateUI()

        // Create an update transaction, and apply the changes.
        prefs.edit()
                .putLong("deadline", deadline.time)
                .apply()
    }

    fun updateUI() {
        val hoursLeft = getHoursLeft()

        // Update the displayed text
        hoursLeftTextView.text = "$hoursLeft hours left"

        // Set the Activity title
        val formattedDeadline = SimpleDateFormat.getDateInstance().format(deadline)
        title = "Deadline: $formattedDeadline"

        // Animate the grim reaper walking towards the computer guy
        reaperImageView.animate().translationX(-10f * hoursLeft).duration = 3000
    }

    private fun getHoursLeft(): Long {
        val now = Date()
        val diff = deadline.time - now.time // Number of milliseconds from now until the deadline

        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) // Convert ms to hours
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun onShareButtonClicked(menuItem: MenuItem) {
        val hoursLeft = getHoursLeft()
        val text = "I have a deadline in just $hoursLeft hours. Help me procrastinate!"

        // We intend to send something
        val intent = Intent(Intent.ACTION_SEND)
        // Here's what we are trying to send
        intent.putExtra(Intent.EXTRA_TEXT, text)
        // Just plain old boring text.
        intent.type = "text/plain"
        // Creates the panel that lets us choose app to share via
        val chooserIntent = Intent.createChooser(intent, "Share your deadline using…")
        // Display the sharing panel
        startActivity(chooserIntent)
    }

}
