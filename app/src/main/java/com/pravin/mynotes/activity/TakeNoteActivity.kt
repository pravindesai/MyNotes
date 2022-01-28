package com.pravin.mynotes.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.pravin.mynotes.room.Note
import com.pravin.mynotes.room.NoteDB
import com.pravin.mynotes.room.NoteDao
import android.widget.TextView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.pravin.mynotes.R
import java.io.FileDescriptor
import java.io.IOException
import java.util.*
import java.lang.Exception

class TakeNoteActivity : BaseActivity() {
    companion object{
        val NEW_NOTE_MODE = "NEW_NOTE"
        val UPDATE_NOTE_MODE = "UPDATE_NOTE"
    }
    lateinit var NOTEDAO:NoteDao


    private var MODE = NEW_NOTE_MODE


    lateinit var dateTv:TextView
    lateinit var imageView:ImageView
    lateinit var cameraIcon: ImageView
    lateinit var titleEt:EditText
    lateinit var noteEt:EditText
    lateinit var button: Button
    lateinit var note: Note

    lateinit var title:String
    lateinit var text:String
    lateinit var bitmap:Bitmap
             var epoch:Long  = System.currentTimeMillis()

    private fun initUI() {
        dateTv    = findViewById(R.id.dateTv)
        imageView = findViewById(R.id.imageView)
        cameraIcon = findViewById(R.id.cameraIcon)
        titleEt = findViewById(R.id.titleEt)
        noteEt = findViewById(R.id.noteEt)
        button = findViewById(R.id.button)
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo_placeholder)

        button.setOnClickListener {

            title    = titleEt.text.toString()
            text     = noteEt.text.toString()

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
                note = Note(0,title, text, bitmap, epoch)
                NOTEDAO.insertNote(note)

            } else if (MODE.equals(UPDATE_NOTE_MODE)) {
                note.Title = title
                note.Text = text
                note.img = bitmap
                note.DayEpochTimeStamp = epoch
                NOTEDAO.insertNote(note)
            }

            val noteMainActivityIntent:Intent = Intent(this, NoteMainActivity::class.java)
            noteMainActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(noteMainActivityIntent)

        }
        imageView.setOnClickListener { cameraIcon.performClick() }
        cameraIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
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
        epoch = intent.getLongExtra("DATE", System.currentTimeMillis())
        MODE  = intent.getStringExtra("MODE").toString()
        initUI()

        val cal = Calendar.getInstance()
        cal.timeInMillis = epoch
        dateTv.setText(getFormattedDate(cal))


        if (MODE.equals(NEW_NOTE_MODE)){
            Log.e(TAG, "onCreate: NEW NOTE" )
        }else{
            note = sharedNote
            title   = note.Title
            text    = note.Text
            bitmap  = note.img!!
            epoch   = note.DayEpochTimeStamp

            titleEt.setText(title)
            noteEt.setText(text)
            dateTv.text = getFormattedDate(epochToCalender(epoch))
            Glide.with(this).load(bitmap).into(imageView)

            button.text = "Update"
        }

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

            val bMap = uriToBitmap(data?.data.toString().toUri())

            try {
                if (bMap != null) {
                    bitmap = bMap
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Glide.with(this).load(bitmap).into(imageView)

        }
    }


    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor: ParcelFileDescriptor? =
                getContentResolver().openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor? = parcelFileDescriptor?.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


}


