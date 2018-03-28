package no.hackerspace_ntnu.deadline;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation") // Don't complain about Date
public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Date deadline = new Date(); // Please don't use `Date` in real apps.

    TextView hoursLeftTextView; // Lets us “do stuff” with the text view.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Look up the text view using the ID we set in `activity_main.xml`
        hoursLeftTextView = findViewById(R.id.hoursLeftTextView);
        // hoursLeftTextView.setText("X hours left"); // Now we update the text!
    }

    /*
     * This code is run when the “Change deadline” button is clicked.
     * We set the onClick listener attribute of the button in `activity_main.xml` to make it happen.
     */
    public void onChangeDeadlineClicked(View viewThatWasClicked) {
        DatePickerDialog pickerDialog = new DatePickerDialog(
                this, // Give dialog some context about where it is being created. An activity is a valid context.
                this, // Tell dialog responses should be delivered right here (more precisely, in onDateSet).
                deadline.getYear() + 1900, // When dialog opens, pre-select current deadline
                deadline.getMonth(),
                deadline.getDate()
        );
        pickerDialog.show(); // Show picker
    }

    /*
     * This code is run when the user taps "OK" in the date picker.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Update our deadline with the selected date
        deadline.setYear(year - 1900);
        deadline.setMonth(month);
        deadline.setDate(dayOfMonth);
        // Assume all deadlines are right before midnight.
        // Use TimePickerDialog if you want a proper solution.
        deadline.setHours(23);
        deadline.setMinutes(50);

        // Ensure number of hours left is updated
        updateUI();
    }

    void updateUI() {
        Date now = new Date();
        long deadlineMillis = deadline.getTime();
        long nowMillis = now.getTime();
        long diff = deadlineMillis - nowMillis; // Number of milliseconds from now until the deadline

        long hoursLeft = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS); // Convert ms to hours

        // Update the displayed text
        hoursLeftTextView.setText(hoursLeft + " hours left");

        // Set the Activity title
        String formattedDeadline = SimpleDateFormat.getDateInstance().format(deadline);
        setTitle("Deadline: " + formattedDeadline);
    }
}