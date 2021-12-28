package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity  : AppCompatActivity() {

    private lateinit var parcel_number_input : EditText;
    private lateinit var parcel_name : EditText;
    private lateinit var parcel_status : EditText;
    private lateinit var update_button : Button;
    private lateinit var delete_button : Button;

    lateinit var id : String
    lateinit var name : String
    lateinit var status : String
    lateinit var number : String

    lateinit var myDB : MyDatabaseHelper

    lateinit var builder : AlertDialog.Builder

    lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.update_view_title);
        setContentView(R.layout.activity_update)

        actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)


        parcel_number_input = findViewById(R.id.parcel_number_input2)
        parcel_name = findViewById(R.id.name_input)
        parcel_status = findViewById(R.id.status_input)
        update_button = findViewById(R.id.update_button)
        delete_button = findViewById(R.id.delete_button)

        getAndSetIntentData()

        update_button.setOnClickListener {
            myDB = MyDatabaseHelper(this)
            name = parcel_name.text.toString().trim()
            status = parcel_status.text.toString().trim()
            number = intent.getStringExtra("number").toString()
            myDB.updateData(id, name, status, number)
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        delete_button.setOnClickListener{
            confirmDialog()
        }
    }

    fun getAndSetIntentData(){
        if (intent.hasExtra("id") && intent.hasExtra("name") &&
            intent.hasExtra("status") && intent.hasExtra("number")){

            id = intent.getStringExtra("id").toString()
            name = intent.getStringExtra("name").toString()
            status = intent.getStringExtra("status").toString()
            number = intent.getStringExtra("number").toString()

            parcel_name.setText(name)
            parcel_status.setText(status)
            parcel_number_input.setText(number)

        }
        else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        }
    }

    fun confirmDialog(){
        builder = AlertDialog.Builder(this)
        builder.setTitle("Delete " + name + " ?")
        builder.setMessage("Are you sure you want to delete the following item: " + name + " ?")
        builder.setPositiveButton("YES") { dialog, which ->
            myDB = MyDatabaseHelper(this)
            myDB.deleteOneRow(id)
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("NO") {dialog, which ->

        }
        builder.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}