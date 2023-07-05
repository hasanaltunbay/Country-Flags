package com.hasanaltunbay.ulkebayrak.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasanaltunbay.ulkebayrak.Adapter.UlkelerAdapter
import com.hasanaltunbay.ulkebayrak.R
import com.hasanaltunbay.ulkebayrak.databinding.FragmentAnaBinding
import com.hasanaltunbay.ulkebayrak.viewModel.AnaViewModel


class AnaFragment : Fragment() {

    private lateinit var binding:FragmentAnaBinding
    private lateinit var viewModel:AnaViewModel
    private val ulkeAdapter=UlkelerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_ana, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setTitle("ÜLKE BAYRAKLARI")

        viewModel=ViewModelProviders.of(this).get(AnaViewModel::class.java)
        viewModel.veriYenile()

        binding.ulkelerListe.layoutManager=LinearLayoutManager(context)
        binding.ulkelerListe.adapter=ulkeAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {

            binding.ulkelerListe.visibility=View.GONE
            binding.ulkeHata.visibility=View.GONE
            binding.ulkeYukleniyorProgressBar.visibility=View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()





    }

    fun observeLiveData(){
        viewModel.ulkeler.observe(viewLifecycleOwner, Observer {ulkeler ->
            ulkeler?.let {
                binding.ulkelerListe.visibility=View.VISIBLE
                ulkeAdapter.ulkelerListeGuncelle(ulkeler)
            }
        })

        viewModel.ulkeHata.observe(viewLifecycleOwner, Observer { hata->
            hata?.let {
                if (it){
                    binding.ulkeHata.visibility=View.VISIBLE
                }else{
                    binding.ulkeHata.visibility=View.GONE
                }
            }
        })

        viewModel.ulkeYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor->
            yukleniyor?.let {
                if (it){
                    binding.ulkeYukleniyorProgressBar.visibility=View.VISIBLE
                    binding.ulkelerListe.visibility=View.GONE
                    binding.ulkeHata.visibility=View.GONE
                }else{
                    binding.ulkeYukleniyorProgressBar.visibility=View.GONE
                }
            }

        })

    }

}