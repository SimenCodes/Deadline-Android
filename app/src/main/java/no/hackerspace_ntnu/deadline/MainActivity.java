package no.hackerspace_ntnu.deadline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
        int hoursLeft = (int) (Math.random() * 100);
        hoursLeftTextView.setText(hoursLeft + " hours left");
    }
}
