package no.hackerspace_ntnu.deadline;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation") // Don't complain about Date
public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Date deadline = new Date(); // Please don't use `Date` in real apps.

    // SharedPreferences lets us store small pieces of information on the device.
    // We will use it to store the deadline
    SharedPreferences prefs;

    TextView hoursLeftTextView; // Lets us “do stuff” with the text view.
    ImageView reaperImageView; // Used to animate the grim reaper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load preferences from disk.
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Read stored deadline
        long deadlineMillis = prefs.getLong("deadline", System.currentTimeMillis());
        deadline.setTime(deadlineMillis);

        // Look up the views using the IDs we set in `activity_main.xml`
        hoursLeftTextView = findViewById(R.id.hoursLeftTextView);
        reaperImageView = findViewById(R.id.reaperImageView);

        reaperImageView.setTranslationX(-1000); // Throw the reaper offscreen
    }

    /**
     * This code is run whenever the app is about to become visible on the screen somehow
     */
    @Override
    protected void onStart() {
        super.onStart(); // DO NOT DELETE THIS LINE!
        updateUI(); // Ensure UI is up-to-date
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

        // Create an update transaction, and apply the changes.
        prefs.edit()
                .putLong("deadline", deadline.getTime())
                .apply();
    }

    void updateUI() {
        long hoursLeft = getHoursLeft();

        // Update the displayed text
        hoursLeftTextView.setText(hoursLeft + " hours left");

        // Set the Activity title
        String formattedDeadline = SimpleDateFormat.getDateInstance().format(deadline);
        setTitle("Deadline: " + formattedDeadline);

        // Animate the reaper walking towards the computer guy
        reaperImageView.animate().translationX(-10 * hoursLeft).setDuration(3000);
    }

    private long getHoursLeft() {
        Date now = new Date();
        long deadlineMillis = deadline.getTime();
        long nowMillis = now.getTime();
        long diff = deadlineMillis - nowMillis; // Number of milliseconds from now until the deadline

        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /*
     * This code is run when Android creates the app bar.
     * Due to historical reasons, it's called an options menu and is not initialized in onCreate
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /*
     * This code is run when the share button in the app bar is clicked
     */
    public void onShareButtonClicked(MenuItem menuItem) {
        String textToSend = "I have a deadline in just " + getHoursLeft() + " hours. Help me procrastinate";

        // We intend to send something
        Intent intent = new Intent(Intent.ACTION_SEND);
        // Here's what we are trying to send
        intent.putExtra(Intent.EXTRA_TEXT, textToSend);
        // Just plain old boring text.
        intent.setType("text/plain");
        // Creates the panel that lets us choose app to share via
        Intent chooserIntent = Intent.createChooser(intent, "Share your deadline using…");
        // Display the sharing panel
        startActivity(chooserIntent);
    }
}
