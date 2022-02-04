package com.bitassyifaproject.acmc.home.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bitassyifaproject.acmc.R
import com.bitassyifaproject.acmc.config.AcmcApp
import com.bitassyifaproject.acmc.databinding.FragmentHomeBinding
import com.bitassyifaproject.acmc.databinding.FragmentLoginBinding
import com.bitassyifaproject.acmc.extra.data.AdapterMoment
import com.bitassyifaproject.acmc.extra.data.AdapterRating
import com.bitassyifaproject.acmc.extra.data.ExtraRepo
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import javax.inject.Inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var dataLogin: SharedPreferences? = null

    @Inject
    lateinit var extraRepo: ExtraRepo
    lateinit var adapterMoment: AdapterMoment
    lateinit var adapterRating: AdapterRating

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as AcmcApp).applicationComponent.inject(this)
        dataLogin = activity?.getSharedPreferences(
            getString(R.string.sp),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id_pegawai = dataLogin?.getString(
            getString(R.string.id_pegawai),
            getString(R.string.default_value)
        )


        val nama_pegawai = dataLogin?.getString(
            getString(R.string.nama_pegawai),
            getString(R.string.default_value)
        )
         binding.apply {
             namaPegawai.text = "Halo, $nama_pegawai"

             val bannerMenu = bannerSlide
             val imageList = ArrayList<SlideModel>()

             extraRepo.resBanner.observe(viewLifecycleOwner, Observer {
                 print("ini $it")
                 it.forEach {
                     val linkImage = "http://202.62.9.138/acmc_api/uploads/banner/${it.name_image}"
                     imageList.add(SlideModel(linkImage,ScaleTypes.FIT))

                 }
                 bannerMenu.setImageList(imageList)
             })
             extraRepo.banner(requireContext())


             rvMoment.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
             extraRepo.resMoment.observe(viewLifecycleOwner, Observer {
                 if (it != null){
                adapterMoment = AdapterMoment(it,requireActivity())
                     rvMoment.adapter  = adapterMoment

                 }
             })
             extraRepo.moment(requireContext())

             rvRating.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
             extraRepo.resRating.observe(viewLifecycleOwner, Observer {
                 if (it!=null){
                     adapterRating = AdapterRating(it,requireActivity())
                     rvRating.adapter = adapterRating
                 }
             })
             extraRepo.rating(requireContext())

         }

    }
}