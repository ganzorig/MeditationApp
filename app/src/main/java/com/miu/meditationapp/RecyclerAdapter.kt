package com.miu.meditationapp

import android.content.Context

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RecyclerAdapter(var context: Context, foods: List<PostHistory>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder?>() {
    lateinit var items: List<PostHistory>
    private val mLayoutInflater: LayoutInflater
    private val count: Long = 1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var postbody: TextView
        var posteddate: TextView

        init {
            username = itemView.findViewById<View>(R.id.textView2) as TextView
            postbody = itemView.findViewById<View>(R.id.textView5) as TextView
            //this.foodIngredient = (TextView) itemView.findViewById(R.id.item_event_ingredient);
            posteddate = itemView.findViewById<View>(R.id.textView6) as TextView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            mLayoutInflater.inflate(R.layout.post_history_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/MONCHICAGO.TTF");
        holder.username.setText(items[position].uid)
        //holder.foodIngredient.setText(foods.get(position).getFoodIngre());

        holder.postbody.setText("Price: " + items[position].postbody)
        holder.posteddate.setText(items[position].posteddate)
        //getCount(customerNumber.getSelectedItem().toString());
        holder.itemView.setOnClickListener(View.OnClickListener {
            Log.e(TAG, "onClick: clicked")
        })
    }



    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }



    companion object {
        private const val TAG = "RecyclerAdapter"
    }

    init {
        this.items = foods
        mLayoutInflater = LayoutInflater.from(context)
    }


}