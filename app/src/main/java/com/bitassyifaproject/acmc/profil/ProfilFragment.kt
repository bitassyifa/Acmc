package com.bitassyifaproject.acmc.profil

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bitassyifaproject.acmc.R
import com.bitassyifaproject.acmc.databinding.FragmentHomeBinding
import com.bitassyifaproject.acmc.databinding.FragmentProfilBinding
import com.bumptech.glide.Glide

class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    var dataLogin: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val namaPegawai = dataLogin?.getString(
            getString(R.string.nama_pegawai),
            getString(R.string.default_value)
        )
        val idpegawai = dataLogin?.getString(
            getString(R.string.id_pegawai),
            getString(R.string.default_value)
        )
        val foto = dataLogin?.getString(
            getString(R.string.foto),
            getString(R.string.default_value)
        )
        val bidang = dataLogin?.getString(
            getString(R.string.bidang),
            getString(R.string.default_value)
        )
        val unit = dataLogin?.getString(
            getString(R.string.unit),
            getString(R.string.default_value)
        )
        val alamat_tinggal = dataLogin?.getString(
            getString(R.string.alamat_tinggal),
            getString(R.string.default_value)
        )
        val tgl_lahir = dataLogin?.getString(
            getString(R.string.tgl_lahir),
            getString(R.string.default_value)
        )
        val noTlp = dataLogin?.getString(
            getString(R.string.no_tlp),
            getString(R.string.default_value)
        )
        val mail = dataLogin?.getString(
            getString(R.string.email_umum),
            getString(R.string.default_value)
        )

        var linkFoto= "http://202.62.9.138:1234/hrd/php/foto/$foto"

        binding.apply {
            profilName.text = namaPegawai
            profilID.text = idpegawai
            bidangProfile.text = bidang
            unitProfile.text = unit
            alamatProfile.text = alamat_tinggal
            birthProfile.text = tgl_lahir
            mailProfile.text = mail
            phoneProfile.text = noTlp
            Glide
                .with(this@ProfilFragment)
                .load(linkFoto)
                .fitCenter()
                .into(profilImage);
        }
    }


}