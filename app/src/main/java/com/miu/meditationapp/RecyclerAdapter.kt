package com.miu.meditationapp

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(var context: Context, items: List<PostHistory>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder?>() {
    lateinit var items: List<PostHistory>
    private val mLayoutInflater: LayoutInflater
    private val count: Long = 1

    init {
        this.items = items
        mLayoutInflater = LayoutInflater.from(context)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var postbody: TextView
        var posteddate: TextView

        init {
            username = itemView.findViewById<View>(R.id.textView2) as TextView
            postbody = itemView.findViewById<View>(R.id.textView5) as TextView
            posteddate = itemView.findViewById<View>(R.id.textView6) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            mLayoutInflater.inflate(R.layout.post_history_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.setText(items[position].uid)
        holder.postbody.setText(items[position].postbody)
        holder.posteddate.setText(items[position].posteddate)
//        holder.itemView.setOnClickListener(View.OnClickListener {
//            Log.e(TAG, "onClick: clicked")
//        })
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
}