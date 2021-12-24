import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import com.example.myapplication2.UpdateActivity

import java.util.ArrayList


class CustomAdapter internal constructor(
    private val activity: Activity,
    private val context: Context,
    private val parcel_id: ArrayList<*>,
    private val parcel_name: ArrayList<*>,
    private val parcel_number: ArrayList<*>,
    private val parcel_status: ArrayList<*>
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.parcel_id_txt.text = parcel_id[position].toString()
        holder.parcel_name_txt.text = parcel_name[position].toString()
        holder.parcel_status_txt.text = parcel_status[position].toString()
        holder.parcel_number_txt.text = parcel_number[position].toString()
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", parcel_id[position].toString())
            intent.putExtra("name", parcel_name[position].toString())
            intent.putExtra("number", parcel_number[position].toString())
            intent.putExtra("status", parcel_status[position].toString())
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return parcel_id.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var parcel_id_txt: TextView
        var parcel_name_txt: TextView
        var parcel_status_txt: TextView
        var parcel_number_txt: TextView
        var mainLayout: LinearLayout

        init {
            parcel_id_txt = itemView.findViewById(R.id.parcel_id_txt)
            parcel_name_txt = itemView.findViewById(R.id.parcel_name_txt)
            parcel_status_txt = itemView.findViewById(R.id.parcel_status_txt)
            parcel_number_txt = itemView.findViewById(R.id.parcel_number_txt)
            mainLayout = itemView.findViewById(R.id.mainLayout)
            //Animate Recyclerview
            val translate_anim = AnimationUtils.loadAnimation(
                context, R.anim.translate_anim
            )
            mainLayout.animation = translate_anim
        }
    }
}
