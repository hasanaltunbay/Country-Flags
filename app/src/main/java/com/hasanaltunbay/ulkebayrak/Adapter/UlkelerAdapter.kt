package com.hasanaltunbay.ulkebayrak.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hasanaltunbay.ulkebayrak.R
import com.hasanaltunbay.ulkebayrak.databinding.SatirUlkeBinding
import com.hasanaltunbay.ulkebayrak.model.Ulke
import com.hasanaltunbay.ulkebayrak.util.downloadFromUrl
import com.hasanaltunbay.ulkebayrak.util.placeholderProgressBar
import com.hasanaltunbay.ulkebayrak.view.AnaFragmentDirections

class UlkelerAdapter(val ulkeList:ArrayList<Ulke>) : RecyclerView.Adapter<UlkelerAdapter.UlkeViewHolder>(){


    class UlkeViewHolder(val binding:SatirUlkeBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UlkeViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<SatirUlkeBinding>(inflater,R.layout.satir_ulke,parent,false)
        return UlkeViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return ulkeList.size
    }

    override fun onBindViewHolder(holder: UlkeViewHolder, position: Int) {

        holder.binding.ulke=ulkeList[position]

/*
        holder.binding.isim.text=ulkeList[position].ulkeIsmi
        holder.binding.bolge.text=ulkeList[position].ulkeBolge
        holder.binding.imageView.downloadFromUrl(ulkeList[position].imageUrl, placeholderProgressBar(holder.binding.root.context))
        */

        holder.binding.cardid.setOnClickListener {
            val action=AnaFragmentDirections.actionAnaFragmentToDetayFragment(ulkeList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }


    }

    fun ulkelerListeGuncelle(yeniUlkelerListe:List<Ulke>){
        ulkeList.clear()
        ulkeList.addAll(yeniUlkelerListe)
        notifyDataSetChanged()
    }



}