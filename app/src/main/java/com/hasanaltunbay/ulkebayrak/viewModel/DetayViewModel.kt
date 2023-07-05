package com.hasanaltunbay.ulkebayrak.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasanaltunbay.ulkebayrak.model.Ulke
import com.hasanaltunbay.ulkebayrak.service.UlkeDatabase
import kotlinx.coroutines.launch

class DetayViewModel(application: Application):BaseViewModel(application) {

    val ulkeLiveData=MutableLiveData<Ulke>()

    fun getDataFromRoom(uuid:Int){

        launch {
            val dao=UlkeDatabase(getApplication()).ulkeDao()
            val ulke=dao.getUlke(uuid)
            ulkeLiveData.value=ulke
        }



    }


}