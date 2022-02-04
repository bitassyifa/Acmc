package com.bitassyifaproject.acmc.order.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bitassyifaproject.acmc.R
import com.bitassyifaproject.acmc.config.AcmcApp
import com.bitassyifaproject.acmc.databinding.ActivityOrderBinding
import com.bitassyifaproject.acmc.home.ui.HomeActivity
import com.bitassyifaproject.acmc.order.data.JenisLayananAdapter
import com.bitassyifaproject.acmc.order.data.OrderInsertModel
import com.bitassyifaproject.acmc.order.data.OrderRepo
import com.bitassyifaproject.acmc.order.data.ProdukAdapter
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class OrderActivity : AppCompatActivity(), View.OnClickListener {
    @Inject
    lateinit var orderRepo: OrderRepo
    lateinit var jenisLayananAdapter: JenisLayananAdapter
    lateinit var produkAdapter: ProdukAdapter
    private lateinit var binding: ActivityOrderBinding

    private val IMAGE_DIRECTORY = "/materi"
    private val BUFFER_SIZE = 1024 * 2
    var calender = Calendar.getInstance()

    private var jenisLayanan : String? = null
    private var produk : String? = null
    private var idPegawai : String? = null
    private var idProduk : String? = null
    private var idJl : String? = null

    var encodePdf : String? = null
    var dataLogin: SharedPreferences? = null
    var convertFile: File? = null
    private lateinit var pdfUri: Uri
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        (this.applicationContext as AcmcApp).applicationComponent.inject(this)
        dataLogin = this.getSharedPreferences(
            getString(R.string.sp),
            Context.MODE_PRIVATE
        )

        requestMultiplePermissions()

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        val namaPegawai = dataLogin?.getString(
            getString(R.string.nama_pegawai),
            getString(R.string.default_value)
        )
         idPegawai = dataLogin?.getString(
            getString(R.string.id_pegawai),
            getString(R.string.default_value)
        )

        val unit = dataLogin?.getString(
            getString(R.string.unit),
            getString(R.string.default_value)
        )
        val no_tlp = dataLogin?.getString(
            getString(R.string.no_tlp),
            getString(R.string.default_value)
        )

        val dateStart = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calender.set(Calendar.YEAR,year)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                updateTgl()
            }
        }

        binding.tgl1.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                DatePickerDialog(this@OrderActivity,
                    dateStart,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
        setContentView(binding.root)
        binding.apply {
            btnOrder.setOnClickListener(this@OrderActivity)
            orderRepo.resJl.observe(this@OrderActivity, Observer {
                jenisLayananAdapter = JenisLayananAdapter(it,this@OrderActivity)
                jlChoice.adapter = jenisLayananAdapter

                jlChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        idJl = it[p2].id_jl
                        jenisLayanan = it[p2].jenis_layanan
                        orderRepo.resProduk.observe(this@OrderActivity, Observer {
                            produkAdapter = ProdukAdapter(it,this@OrderActivity)
                            produkChoice.adapter = produkAdapter
                            produkChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(
                                    p0: AdapterView<*>?,
                                    p1: View?,
                                    p2: Int,
                                    p3: Long
                                ) {
                                    idProduk = it[p2].id_produk
                                    produk = it[p2].nama_produk
                                }

                                override fun onNothingSelected(p0: AdapterView<*>?) {

                                }

                            }
                        })
                        orderRepo.produk(idJl.toString(),this@OrderActivity )
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }
            })
            orderRepo.JenisLayanan(this@OrderActivity)
            namaTv.text = namaPegawai
            unitTv.text = unit
            noTlpTv.text = no_tlp
            chooseFile.setOnClickListener(this@OrderActivity)

        }
    }

    private fun updateTgl() {
        val formatTgl = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(formatTgl,Locale.US)
        binding.tgl1.text = sdf.format(calender.time)
    }


    private fun requestMultiplePermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(
                            applicationContext,
                            "All permissions are granted by user!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(applicationContext, "Some Error! ", Toast.LENGTH_SHORT).show()
            }
            .onSameThread()
            .check()
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
//                101 -> {
//                    data?.data?.also { uri ->
//                        Log.i(TAG, "Uri: $uri")
//                        baseAdapter?.add(ImageArray(null, null, uri.toString()))
//                    }
//                }
                111 -> {
                    data?.data?.also { documentUri ->
                        this.contentResolver?.takePersistableUriPermission(
                            documentUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        val file = DocumentUtils.getFile(this,documentUri) //use pdf as file
                        convertFile = file
                        binding.nameFile.text = file.name
                    }
                }
            }
        }
    }



    override fun onClick(v: View?) {
      when (v) {
          binding.chooseFile ->{
              val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                  type = "application/pdf"
                  addCategory(Intent.CATEGORY_OPENABLE)
                  flags = flags or Intent.FLAG_GRANT_READ_URI_PERMISSION
              }
              startActivityForResult(intent, 111)
          }
          binding.btnOrder -> {
              val data = OrderInsertModel(
                  id_pegawai =  idPegawai.toString(),
                  nama_pegawai = binding.namaTv.text.toString(),
                  unit = binding.unitTv.text.toString(),
                  no_tlp = binding.noTlpTv.text.toString(),
                  id_jl = idJl.toString(),
                  id_produk = idProduk.toString(),
                  catatan = binding.notes.text.toString(),
                  estimasi_tgl = binding.tgl1.text.toString()
              )

              val user_msg_error: String = binding.tgl1.text.toString()
              if(user_msg_error.trim().isEmpty()) {
//                  binding.tgl1.error = "Required"
                  Toast.makeText(this, "Tanggal Estimasi wajib di isi ", Toast.LENGTH_SHORT).show()
              } else {
                  val builder = AlertDialog.Builder(this)
                  builder.setTitle("Konfirmasi Order")
                  builder.setMessage("Order Detail :\n" +
                          "ID Pegawai : ${data.id_pegawai}\n"+
                          "Nama Pegawai : ${data.nama_pegawai}\n"+
                          "Unit : ${data.unit}\n"+
                          "Jenis Layanan : $jenisLayanan \n"+
                          "Produk : $produk \n"+
                          "Catatan : ${data.catatan}\n"+
                          "Tanggal Estimasi :${data.estimasi_tgl} ")
                  builder.setPositiveButton("Ya") { dialog, which ->
                    if (convertFile == null){
                        println("FILE NULL")
                    } else {
                        orderRepo.resApi.observe(this, Observer {
                            if (it.status){
                                Toast.makeText(this, "Order sukses", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,HomeActivity::class.java))
                            } else {
                                "Order Failed"
                            }
                        })
                        orderRepo.order(this,data, convertFile!!)

                    }                  }

                  builder.setNegativeButton("Tidak"){dialog,which ->

                  }
                  val dialog: AlertDialog = builder.create()
                  dialog.show()
              }
          }
      }

    }

    object DocumentUtils {
        fun getFile(mContext: OrderActivity, documentUri: Uri): File {
            val inputStream = mContext?.contentResolver?.openInputStream(documentUri)
            var file =  File("")
            inputStream.use { input ->
                file =
                    File(mContext?.cacheDir, System.currentTimeMillis().toString()+".pdf")
                FileOutputStream(file).use { output ->
                    val buffer =
                        ByteArray(4 * 1024) // or other buffer size
                    var read: Int = -1
                    while (input?.read(buffer).also {
                            if (it != null) {
                                read = it
                            }
                        } != -1) {
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                }
            }
            return file
        }
    }

}