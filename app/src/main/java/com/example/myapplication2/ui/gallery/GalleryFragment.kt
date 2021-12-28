package com.example.myapplication2.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication2.MainActivity
import com.example.myapplication2.MyDatabaseHelper
import com.example.myapplication2.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null

    private val binding get() = _binding!!

    private lateinit var submit_button: Button
    private lateinit var scan_button: Button
    private lateinit var input: EditText

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


        myDB = MyDatabaseHelper(context)

        submit_button.setOnClickListener {

            if (input.text.toString() == "") {
                Toast.makeText(context, "Input field can not be empty", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            } else {
                myDB.addParcel(input.text.toString())
//                myDB.addParcel("TESTPACKAGEATPICKUPPOINT")
                intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        scan_button.setOnClickListener {Toast.makeText(context, "TODO", Toast.LENGTH_SHORT).show();}
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}