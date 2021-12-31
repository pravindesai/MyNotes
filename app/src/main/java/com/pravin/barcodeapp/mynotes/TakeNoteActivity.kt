package com.pravin.barcodeapp.mynotes

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class TakeNoteActivity : BaseActivity() {

    lateinit var imageView:ImageView
    lateinit var cameraIcon: ImageView
    lateinit var titleEt:EditText
    lateinit var noteEt:EditText
    lateinit var button: Button

    private fun initUI() {
        imageView = findViewById(R.id.imageView)
        cameraIcon = findViewById(R.id.cameraIcon)
        titleEt = findViewById(R.id.titleEt)
        noteEt = findViewById(R.id.noteEt)
        button = findViewById(R.id.button)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_note)
        setTitle("Add Note")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        initUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
            else ->{
                Log.e(TAG, "onOptionsItemSelected: NOT FOUND "+item.itemId )
            }
        }
        return true
    }


}