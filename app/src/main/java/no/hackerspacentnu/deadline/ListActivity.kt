package no.hackerspacentnu.deadline

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

class ListActivity : AppCompatActivity() {

    // When displaying data, we interact with an Adapter, not the RecyclerView itself.
    val deadlineAdapter = DeadlineAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerView.apply {
            // Tell RecyclerView where to go when it needs to present or create new views.
            adapter = deadlineAdapter
            // We want the items stacked vertically
            layoutManager = LinearLayoutManager(context)
            // Optional: RecyclerView can speed things up a bit if
            // we tell it that items have equal height
            setHasFixedSize(true)
        }

        // Insert some deadlines. Extra exercise: create a GUI to add deadlines :)
        val now = Date()
        deadlineAdapter.items = listOf(
                Deadline("Android-workshop", 4, 18),
                Deadline("Cogito-kurs", 4, 24),
                Deadline("DB øving 4", 4, 14),
                Deadline("PU Sprint 3", 4, 16),
                Deadline("MMI-Øving 5", 4, 30),
                Deadline("SL innlevering 3", 4, 30),
                Deadline("Eksamen KTN", 5, 15),
                Deadline("Eksamen SL", 5, 24),
                Deadline("Eksamen DB", 5, 30),
                Deadline("Eksamen MMI", 6, 5)
        ).filter { it.date.after(now) } // Don't display yesterday's deadlines
    }

}
