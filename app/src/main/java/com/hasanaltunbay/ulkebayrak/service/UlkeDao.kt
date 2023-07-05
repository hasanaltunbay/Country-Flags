package com.hasanaltunbay.ulkebayrak.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hasanaltunbay.ulkebayrak.model.Ulke

@Dao
interface UlkeDao {

    @Insert
    suspend fun insertAll(vararg ulke:Ulke):List<Long>

    @Query("SELECT * FROM ulke")
    suspend fun getTumUlkeler():List<Ulke>

    @Query("SELECT * FROM ulke WHERE uuid=:ulkeId")
    suspend fun getUlke(ulkeId:Int):Ulke

    @Query("DELETE FROM ulke")
    suspend fun deleteTumUlkeler()


}