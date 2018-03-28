package no.hackerspacentnu.deadline

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION") // Don't complain about Date
class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    val deadline = Date() // Please don't use `Date` in real apps.

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
    }

    fun updateUI() {
        val now = Date()
        val diff = deadline.time - now.time // Number of milliseconds from now until the deadline

        val hoursLeft = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) // Convert ms to hours

        // Update the displayed text
        hoursLeftTextView.text = "$hoursLeft hours left"

        // Set the Activity title
        val formattedDeadline = SimpleDateFormat.getDateInstance().format(deadline)
        title = "Deadline: $formattedDeadline"
    }
}
