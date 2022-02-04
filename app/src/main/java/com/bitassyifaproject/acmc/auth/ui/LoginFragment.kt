package com.bitassyifaproject.acmc.auth.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bitassyifaproject.acmc.R
import com.bitassyifaproject.acmc.auth.data.LoginModel
import com.bitassyifaproject.acmc.auth.data.LoginRepo
import com.bitassyifaproject.acmc.config.AcmcApp
import com.bitassyifaproject.acmc.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController
    var dataLogin: SharedPreferences? = null

    @Inject
    lateinit var loginRepo: LoginRepo

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if (dataLogin?.contains(getString(R.string.id_pegawai))!! && dataLogin?.contains(getString(R.string.login_method_key))!! ){
            view.findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
        } else {
            Toast.makeText(
                    this.requireContext(),
                    "Login terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
        }

        binding.apply {
            btnLogin.setOnClickListener(this@LoginFragment)
        }

        loginRepo.resAPI.observe(viewLifecycleOwner, Observer {
            if (!it.status){
//                Toast.makeText(
//                    this.requireContext(),
//                    "Id atau Password salah! \n Cobalagi",
//                    Toast.LENGTH_SHORT
//                ).show()
            }   else {
                loginRepo.resLogin.observe(viewLifecycleOwner, Observer {
                    if (it != null){
                        with(dataLogin?.edit()){
                            this?.putString(getString(R.string.username), it.email)
                            this?.putString(getString(R.string.id_pegawai), it.id_pegawai)
                            this?.putString(getString(R.string.nama_pegawai), it.nama_pegawai)
                            this?.putString(getString(R.string.nip), it.nip)
                            this?.putString(getString(R.string.foto), it.foto)
                            this?.putString(getString(R.string.no_tlp), it.no_telp)
                            this?.putString(getString(R.string.unit), it.unit)
                            this?.putString(getString(R.string.bidang), it.bidang)
                            this?.putString(getString(R.string.alamat_tinggal), it.alamat_tinggal)
                            this?.putString(getString(R.string.email_umum), it.email_umum)
                            this?.putString(getString(R.string.tgl_lahir), it.tgl_lahir)
                            this?.putString(
                                getString(R.string.login_method_key),
                                "appLogin"
                            )
                            this?.commit()
                        }
                        navController.navigate(R.id.action_loginFragment_to_homeActivity)
                    }
                })
            }
        })
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnLogin -> {
                val loginModel = LoginModel(
                    username = binding.authUsername.text.toString(),
                    password = binding.authPassword.text.toString()
                )

                if (binding.authUsername.text.toString() == "" && binding.authPassword.text.toString()==""){
                    Toast.makeText(this.context, "Isi semua form", Toast.LENGTH_SHORT).show()
                } else {
                    loginRepo.login(loginModel,requireContext())
                }
            }
        }
    }
}