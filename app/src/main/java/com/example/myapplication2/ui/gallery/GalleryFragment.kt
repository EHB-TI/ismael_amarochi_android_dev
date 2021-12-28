package com.example.myapplication2.ui.gallery

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication2.MainActivity
import com.example.myapplication2.MyDatabaseHelper
import com.example.myapplication2.R
import com.example.myapplication2.databinding.FragmentGalleryBinding
import com.google.zxing.integration.android.IntentIntegrator


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    private val binding get() = _binding!!

    private lateinit var submit_button: Button
    private lateinit var scan_button: Button
    private lateinit var input: EditText

    private lateinit var scanContent : String

    private lateinit var myDB: MyDatabaseHelper

    private lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val view: View =
            inflater.inflate(com.example.myapplication2.R.layout.fragment_gallery, container, false)

        input = view.findViewById(com.example.myapplication2.R.id.parcel_number_input)
        submit_button = view.findViewById(com.example.myapplication2.R.id.submit_button)
        scan_button = view.findViewById(com.example.myapplication2.R.id.scan_button)

        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                scanBarcode()
            }
            else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }

        myDB = MyDatabaseHelper(context)

        submit_button.setOnClickListener {

            if (input.text.toString() == "") {
                Toast.makeText(context, "Input field can not be empty", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            } else {
                if (input.text.toString() == "TESTPACKAGEATPICKUPPOINT" || input.text.toString() == "TESTPACKAGELOADEDFORDELIVERY" ||
                    input.text.toString() == "TESTPACKAGEEDI" || input.text.toString() == "TESTPACKAGEDELIVERED" ){
                    myDB.addParcel(input.text.toString())

                    intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "Only following numbers are allowed in order to function propperly: " +
                            "TESTPACKAGEATPICKUPPOINT, TESTPACKAGEEDI, TESTPACKAGELOADEDFORDELIVERY, TESTPACKAGEDELIVERED",
                        Toast.LENGTH_LONG).show();
                }
            }
        }

        scan_button.setOnClickListener {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun scanBarcode(){
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setOrientationLocked(true)
        integrator.setPrompt(getString(R.string.barcode_scan_text))
        integrator.setBeepEnabled(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Scanned : " + result.contents, Toast.LENGTH_LONG).show()
                scanContent = result.contents
                myDB.addParcel(scanContent)
                intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}