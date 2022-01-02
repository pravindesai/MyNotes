package com.pravin.barcodeapp.mynotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.pravin.barcodeapp.mynotes.room.Note
import com.pravin.barcodeapp.mynotes.room.NoteDB
import com.pravin.barcodeapp.mynotes.room.NoteDB_Impl
import com.pravin.barcodeapp.mynotes.room.NoteDao
import java.time.Instant
import androidx.core.app.ActivityCompat.startActivityForResult
import android.provider.MediaStore
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*


class TakeNoteActivity : BaseActivity() {
    lateinit var NOTEDAO:NoteDao

    private val NEW_NOTE_MODE = "NEW_NOTE"
    private val UPDATE_NOTE_MODE = "UPDATE_NOTE"
    private var MODE = NEW_NOTE_MODE


    lateinit var dateTv:TextView
    lateinit var imageView:ImageView
    lateinit var cameraIcon: ImageView
    lateinit var titleEt:EditText
    lateinit var noteEt:EditText
    lateinit var button: Button

    lateinit var title:String
    lateinit var text:String
    var imgPath:String = ""
    var epoch:Long  = System.currentTimeMillis()

    private fun initUI() {
        dateTv    = findViewById(R.id.dateTv)
        imageView = findViewById(R.id.imageView)
        cameraIcon = findViewById(R.id.cameraIcon)
        titleEt = findViewById(R.id.titleEt)
        noteEt = findViewById(R.id.noteEt)
        button = findViewById(R.id.button)


        button.setOnClickListener {

            title    = titleEt.text.toString()
            text     = noteEt.text.toString()
            epoch    = System.currentTimeMillis()

            if (title.isEmpty()||title.isBlank()) {
                titleEt.setError("Required")
                titleEt.requestFocus()
                return@setOnClickListener
            }
            if (text.isEmpty()||text.isBlank()) {
                noteEt.setError("Required")
                noteEt.requestFocus()
                return@setOnClickListener
            }

            if (MODE.equals(NEW_NOTE_MODE)) {
                var note:Note = Note(0,title, text, imgPath, epoch)
                NOTEDAO.insertNote(note)

            } else if (MODE.equals(UPDATE_NOTE_MODE)) {

            }

            val noteMainActivityIntent:Intent = Intent(this,NoteMainActivity::class.java)
            startActivity(noteMainActivityIntent)

        }
        imageView.setOnClickListener { cameraIcon.performClick() }
        cameraIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "image/*"
            ACTIVITYONRESULT_IMG_LAUNCHER.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_note)
        setTitle("Add Note")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        NOTEDAO = NoteDB.getInstance(this)!!.noteDao()
        initUI()

            MODE = NEW_NOTE_MODE
            dateTv.setText(getFormattedDate(Calendar.getInstance()))

//        if (intent.hasExtra("str") == true){
//            MODE = NEW_NOTE_MODE
//        }else{
//            MODE = UPDATE_NOTE_MODE
//        }

        Log.e(TAG, "onStart: MODE "+MODE )

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

    var ACTIVITYONRESULT_IMG_LAUNCHER = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imgPath = data?.data.toString()
            Glide.with(this).load(imgPath).into(imageView)
            Log.e(TAG, ": "+imgPath )
        }
    }


}


