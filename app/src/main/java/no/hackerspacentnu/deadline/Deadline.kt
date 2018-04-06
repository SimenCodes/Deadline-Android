package no.hackerspacentnu.deadline

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple data class to hold onto a deadline's date and name.
 */
@Suppress("DEPRECATION")
data class Deadline(var title: String, val date: Date) {

    /**
     * Optional: Extra constructor so we have a short syntax when coding many deadlines
     */
    constructor(title: String, month: Int, date: Int) : this(title, Date()) {
        this.date.month = month - 1 // Date does 0-indexing on month numbers…
        this.date.date = date // …but not on day of month.
        this.date.hours = 23
        this.date.minutes = 0
    }

    /**
     * Our old `getHoursLeft` method.
     */
    fun calculateHoursLeft(): Long {
        val now = Date()
        val diff = date.time - now.time // Number of milliseconds from now until the deadline
        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) // Convert ms to hours
    }
}
