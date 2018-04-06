package no.hackerspacentnu.deadline

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * Simple class to hold on to references to the views we interact with.
 */
class DeadlineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val hoursLeftTextView: TextView = view.findViewById(R.id.hoursLeftTextView)
    val reaperImageView: ImageView = view.findViewById(R.id.reaperImageView)

    /**
     * Essentially our old `updateUI()` method.
     */
    fun bind(deadline: Deadline) {
        reaperImageView.translationX = -1500f

        val hoursLeft = deadline.calculateHoursLeft()

        // Update the displayed text
        hoursLeftTextView.text = itemView.resources.getString(
                R.string.hours_left_list_item,
                deadline.title,
                hoursLeft
        )

        // Animate the grim reaper walking towards the computer guy
        reaperImageView.animate()
                .translationX(-2f * hoursLeft)
                .setDuration(2000)
                // Optional: Make sure the reaper keeps walking slowly if the app is open for a long time
                .withEndAction {
                    reaperImageView.animate()
                            .setDuration(hoursLeft * 60 * 60 * 1000)
                            .translationX(0f)
                }
    }
}