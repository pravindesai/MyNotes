package com.pravin.barcodeapp.mynotes.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize
import java.io.ByteArrayOutputStream
import java.io.Serializable

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var Id:Int,
    var Title:String,
    var Text:String,
    var img: Bitmap?,
    var DayEpochTimeStamp: Long

):Parcelable {
    override fun describeContents(): Int {
      return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(Id)
        dest?.writeString(Title)
        dest?.writeParcelable(img, flags)
        dest?.writeLong(DayEpochTimeStamp)
    }
}