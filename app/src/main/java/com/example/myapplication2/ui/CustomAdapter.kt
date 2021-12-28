import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import com.example.myapplication2.UpdateActivity


class CustomAdapter(private val activity: Fragment, private val context: Context?, private val parcel_id: ArrayList<String>,
                    private var parcel_name: ArrayList<String>,
                    private var parcel_status: ArrayList<String>,
                    private var parcel_number: ArrayList<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val parcel_id_txt: TextView
        val parcel_name_txt: TextView
        val parcel_status_txt: TextView
        val parcel_number_txt: TextView
        val mainLayout: LinearLayout

        init {
            parcel_id_txt = itemView.findViewById(R.id.parcel_id_txt)
            parcel_name_txt = itemView.findViewById(R.id.parcel_name_txt)
            parcel_status_txt = itemView.findViewById(R.id.parcel_status_txt)
            parcel_number_txt = itemView.findViewById(R.id.parcel_number_txt)
            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_row, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.parcel_id_txt.text = parcel_id[position]
        viewHolder.parcel_name_txt.text = parcel_name[position]
        viewHolder.parcel_status_txt.text = parcel_status[position]
        viewHolder.parcel_number_txt.text = parcel_number[position]
        viewHolder.mainLayout.setOnClickListener {
            val intent : Intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", parcel_id[position])
            intent.putExtra("name", parcel_name[position])
            intent.putExtra("status", parcel_status[position])
            intent.putExtra("number", parcel_number[position])
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return parcel_id.size
    }
}