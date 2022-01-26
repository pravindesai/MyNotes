package com.pravin.barcodeapp.mynotes

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pravin.barcodeapp.mynotes.room.Note
import java.io.File
import android.provider.MediaStore

import android.graphics.Bitmap
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory

import android.os.ParcelFileDescriptor
import java.io.FileDescriptor
import java.io.IOException


class NotesAdapter(val context:NoteMainActivity, val notesList: List<Note>, val onNoteClickListner:OnNoteListner): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class NoteCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var timeTv:          TextView = view.findViewById(R.id.timeTv)
        var selectedImgView: ImageView = view.findViewById(R.id.selectedImgView)
        var cardTitleTv:     TextView = view.findViewById(R.id.cardTitleTv)
        var card_noteTv:     TextView = view.findViewById(R.id.card_noteTv)
        var deleteImg:     ImageView = view.findViewById(R.id.moreImgView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_card, parent, false)
        return NoteCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = notesList.get(position)
        val cardHolder = holder as NoteCardViewHolder

        cardHolder.timeTv.text = epochToString(note.DayEpochTimeStamp)
        cardHolder.cardTitleTv.text = note.Title
        cardHolder.card_noteTv.text = note.Text

        if (note.img!=null){
            Glide.with(cardHolder.itemView.context).load(note.img).into(holder.selectedImgView)
        }

        cardHolder.deleteImg.setOnClickListener { onNoteClickListner.onNoteDeleteClicked(note, position) }
        cardHolder.itemView.setOnClickListener { onNoteClickListner.onNoteClicked(note, position) }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun epochToString(time: Long): String {
        val cal = (context).epochToCalender(time)
        val dateString = (context).getFormattedDate(cal)
        return dateString
    }

}