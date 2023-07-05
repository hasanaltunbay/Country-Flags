package com.hasanaltunbay.ulkebayrak.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasanaltunbay.ulkebayrak.model.Ulke
import com.hasanaltunbay.ulkebayrak.service.UlkeAPIService
import com.hasanaltunbay.ulkebayrak.service.UlkeDatabase
import com.hasanaltunbay.ulkebayrak.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class AnaViewModel(application: Application):BaseViewModel(application) {

    private val ulkeApiService=UlkeAPIService()
    private val disposable=CompositeDisposable()
    private var customPreferences=CustomSharedPreferences(getApplication())
    private var refreshTime=10*60*1000*1000*1000L



    val ulkeler=MutableLiveData<List<Ulke>>()
    val ulkeHata=MutableLiveData<Boolean>()
    val ulkeYukleniyor=MutableLiveData<Boolean>()

    fun veriYenile(){

        val updateTime=customPreferences.getTime()
        if (updateTime!=null && updateTime!=0L && System.nanoTime()-updateTime<refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }
    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite(){
        ulkeYukleniyor.value=true

        launch {
            val ulke =UlkeDatabase(getApplication()).ulkeDao().getTumUlkeler()
            ulkeleriGoster(ulke)
            Toast.makeText(getApplication(),"Ülkeler SQLite'dan geldi",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(){

        ulkeYukleniyor.value=true

        disposable.add(
            ulkeApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Ulke>>(){
                    override fun onSuccess(t: List<Ulke>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"Ülkeler API'dan geldi",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        ulkeYukleniyor.value=false
                        ulkeHata.value=true
                        e.printStackTrace()

                    }

                })
        )
    }
    private fun ulkeleriGoster(ulkeList:List<Ulke>){
        ulkeler.value=ulkeList
        ulkeHata.value=false
        ulkeYukleniyor.value=false
    }
    private fun storeInSQLite(liste:List<Ulke>){

        launch {
            val dao=UlkeDatabase(getApplication()).ulkeDao()
            dao.deleteTumUlkeler()
            val listLong= dao.insertAll(*liste.toTypedArray())
            var i=0
            while (i<liste.size){
                liste[i].uuid=listLong[i].toInt()
                i++
            }
            ulkeleriGoster(liste)
        }

        customPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}