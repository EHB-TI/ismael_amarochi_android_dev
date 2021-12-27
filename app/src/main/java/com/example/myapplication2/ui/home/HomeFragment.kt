package com.example.myapplication2.ui.home

import CustomAdapter
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
            //TODO add empty image and text
        }
        else {
            while(cursor.moveToNext()){
                parcel_id.add(cursor.getString(0))
                parcel_name.add(cursor.getString(1))
                parcel_status.add(cursor.getString(2))
                parcel_number.add(cursor.getString(3))
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//
//
//
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.animation.AnimationUtils
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.annotation.RequiresApi
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.RecyclerView
//import com.example.myapplication2.R
//import com.example.myapplication2.UpdateActivity
//
//import java.util.ArrayList
//
//
//class CustomAdapter internal constructor(
//    private val activity: Fragment,
//    private val context: Context?,
//    private val parcel_id: ArrayList<String>,
//    private val parcel_name: ArrayList<String>,
//    private val parcel_number: ArrayList<String>,
//    private val parcel_status: ArrayList<String>
//) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val inflater = LayoutInflater.from(context)
//        val view: View = inflater.inflate(R.layout.my_row, parent, false)
//        return MyViewHolder(view)
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.parcel_id_txt.setText(parcel_id.get(position))
//        holder.parcel_name_txt.setText(parcel_name.get(position))
//        holder.parcel_status_txt.setText(parcel_status.get(position))
//        holder.parcel_number_txt.setText(parcel_number.get(position))
//        //Recyclerview onClickListener
//        holder.mainLayout.setOnClickListener {
//            val intent = Intent(context, UpdateActivity::class.java)
//            intent.putExtra("id", parcel_id.get(position))
//            intent.putExtra("name", parcel_name.get(position))
//            intent.putExtra("number", parcel_number.get(position))
//            intent.putExtra("status", parcel_status.get(position))
//            activity.startActivityForResult(intent, 1)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return parcel_id.size
//    }
//
//    inner class MyViewHolder(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//        var parcel_id_txt: TextView
//        var parcel_name_txt: TextView
//        var parcel_status_txt: TextView
//        var parcel_number_txt: TextView
//        var mainLayout: LinearLayout
//
//        init {
//            parcel_id_txt = itemView.findViewById(R.id.parcel_id_txt)
//            parcel_name_txt = itemView.findViewById(R.id.parcel_name_txt)
//            parcel_status_txt = itemView.findViewById(R.id.parcel_status_txt)
//            parcel_number_txt = itemView.findViewById(R.id.parcel_number_txt)
//            mainLayout = itemView.findViewById(R.id.mainLayout)
//            //Animate Recyclerview
//            val translate_anim = AnimationUtils.loadAnimation(
//                context, R.anim.translate_anim
//            )
//            mainLayout.animation = translate_anim
//        }
//    }
//}



