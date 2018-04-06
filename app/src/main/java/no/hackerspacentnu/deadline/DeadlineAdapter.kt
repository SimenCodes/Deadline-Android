package no.hackerspacentnu.deadline

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class DeadlineAdapter : RecyclerView.Adapter<DeadlineViewHolder>() {

    var items: List<Deadline> = emptyList()
        set(value) {
            notifyDataSetChanged() // Tell RecyclerView we got some new data
            field = value // Put the value we got into the field
        }

    /**
     * Called when RecyclerView starts up, when the user is scrolling faster than we can
     * recycle old view holders, or "when RecyclerView feels like it".
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeadlineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // Read the layout for each item from the item_deadline file.
        // Don't attach it to the root, RecyclerView will attach it when it's time to display this view
        val view = layoutInflater.inflate(R.layout.item_deadline, parent, false)
        // Call the view holder constructor, which will prepare the layout for use
        return DeadlineViewHolder(view)
    }

    /**
     * Tell the RecyclerView how many items are in our list.
     * (Notice how one-liner methods don't need {} around them.)
     */
    override fun getItemCount() = items.size

    /**
     * Called when RecyclerView wants to display an item.
     * The view holder might be recycled, we need to completely reset it.
     */
    override fun onBindViewHolder(holder: DeadlineViewHolder, position: Int) {
        val deadline = items[position]
        holder.bind(deadline)
    }

}