package com.hasanaltunbay.ulkebayrak.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hasanaltunbay.ulkebayrak.model.Ulke

@Database(entities = arrayOf(Ulke::class), version = 1)
abstract class UlkeDatabase:RoomDatabase() {

    abstract fun ulkeDao():UlkeDao

    companion object{

        @Volatile
        private var instance:UlkeDatabase?=null

        private var lock=Any()

        operator fun invoke(context:Context)= instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance=it
            }
        }

        private fun makeDatabase(context:Context)=Room.databaseBuilder(
            context.applicationContext,UlkeDatabase::class.java,"ulkedatabase"
        ).build()

    }
}