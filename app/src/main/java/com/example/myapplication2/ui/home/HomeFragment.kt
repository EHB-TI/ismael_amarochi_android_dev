package com.example.myapplication2.ui.home

import CustomAdapter
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.MyDatabaseHelper
import com.example.myapplication2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView : RecyclerView

    private lateinit var myDB : MyDatabaseHelper
    private lateinit var parcel_id : ArrayList<String>
    private lateinit var parcel_name : ArrayList<String>
    private lateinit var parcel_status : ArrayList<String>
    private lateinit var parcel_number : ArrayList<String>
    private lateinit var customAdapter : CustomAdapter
    private lateinit var no_data : TextView

    private lateinit var builder : AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val view: View = inflater.inflate(com.example.myapplication2.R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(com.example.myapplication2.R.id.recyclerView)
        no_data = view.findViewById(com.example.myapplication2.R.id.no_data)

        myDB = MyDatabaseHelper(context);

        parcel_id = ArrayList();
        parcel_name = ArrayList();
        parcel_status = ArrayList();
        parcel_number = ArrayList();

        storeDataInArrays()

        customAdapter = CustomAdapter(this, context, parcel_id, parcel_name, parcel_status, parcel_number)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun storeDataInArrays(){
        val cursor : Cursor = myDB.readAllData()
        if (cursor == null){
            no_data.setVisibility(View.VISIBLE);
        }
        else {
            while(cursor.moveToNext()){
                parcel_id.add(cursor.getString(0))
                parcel_name.add(cursor.getString(1))
                parcel_status.add(cursor.getString(2))
                parcel_number.add(cursor.getString(3))
            }
            no_data.setVisibility(View.INVISIBLE);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
