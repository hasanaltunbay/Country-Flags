package com.hasanaltunbay.ulkebayrak.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hasanaltunbay.ulkebayrak.R
import com.hasanaltunbay.ulkebayrak.databinding.FragmentAnaBinding
import com.hasanaltunbay.ulkebayrak.databinding.FragmentDetayBinding
import com.hasanaltunbay.ulkebayrak.util.downloadFromUrl
import com.hasanaltunbay.ulkebayrak.util.placeholderProgressBar
import com.hasanaltunbay.ulkebayrak.viewModel.DetayViewModel


class DetayFragment : Fragment() {

    private lateinit var binding:FragmentDetayBinding

    private lateinit var viewModel:DetayViewModel
    private var ulkeUuid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_detay, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let {
            ulkeUuid= DetayFragmentArgs.fromBundle(it!!).ulkeUuid
        }

        viewModel=ViewModelProviders.of(this).get(DetayViewModel::class.java)
        viewModel.getDataFromRoom(ulkeUuid)


        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.ulkeLiveData.observe(viewLifecycleOwner, Observer {ulke->
            ulke?.let {

                binding.ulke=ulke

                /*binding.ulkeIsim.text=ulke.ulkeIsmi
                binding.ulkeBaskent.text=ulke.ulkeBaskent
                binding.ulkeBolge.text=ulke.ulkeBolge
                binding.ulkeParaBirimi.text=ulke.ulkeParaBirimi
                binding.ulkeDil.text=ulke.ulkeDil*/

                context?.let {
                    binding.ulkeResim.downloadFromUrl(ulke.imageUrl, placeholderProgressBar(it))
                }

            }

        })
    }

}