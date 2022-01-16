package com.example.simpletodo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListerner {

            override fun onItemLongClicked(position: Int) {
                //1.Remove the item from the list
                listOfTasks.removeAt(position)
                //2.Notify the adapter that data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }
        //1. When the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
            //This executes when the user clicks on the button
//            Log.i("Sona", "User clicked on button")
//        }

        loadItems()


        //look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        //Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up the button and input field, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //Get a reference to the button
        // and then set an onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the test the user has inputted into @id/addTaskField
            val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString()

            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            //Notifying the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            //3. Reset text field
            inputTextField.setText("")

            saveItems()
        }
    }

    //Save the data that the user has inputted
    //Save data by writing and reading from a file
    //Get the file we need
    fun getDataFile(): File {

        //every line is going to represent a specific task in our list of tasks

        return File(filesDir, "data.txt")
    }

    //Create a method to get the file we need

    //Load the items by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){

        }
    }
    //Save items by writing them into our data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}