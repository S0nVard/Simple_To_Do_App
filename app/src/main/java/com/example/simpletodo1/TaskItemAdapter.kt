package com.example.simpletodo1

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A bridge that tells the recyclerview how to display our data
 */
class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListerner: OnLongClickListerner):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>()  {

    interface OnLongClickListerner {
        fun onItemLongClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get the data model based on the posit
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Store references to elements in our layout view
        val textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListerner.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}