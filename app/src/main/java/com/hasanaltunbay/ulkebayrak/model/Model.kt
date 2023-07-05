package com.hasanaltunbay.ulkebayrak.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Ulke(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val ulkeIsmi:String?,

    @ColumnInfo(name = "region")
    @SerializedName("region")
    val ulkeBolge:String?,

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    val ulkeBaskent:String?,

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val ulkeParaBirimi:String?,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    val ulkeDil:String?,

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    val imageUrl:String?)
{
    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}