package com.miu.meditationapp

import android.annotation.SuppressLint
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.miu.meditationapp.models.PostHistory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_about.view.*

class RecyclerAdapter(var context: Context, items: List<PostHistory>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder?>() {
    lateinit var items: List<PostHistory>
    private val mLayoutInflater: LayoutInflater
    private val count: Long = 1

    init {
        this.items = items
        mLayoutInflater = LayoutInflater.from(context)
        FirebaseAuth.getInstance().uid
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var postbody: TextView
        var posteddate: TextView
        var image: ImageView

        init {
            username = itemView.findViewById<View>(R.id.name) as TextView
            postbody = itemView.findViewById<View>(R.id.chatContent) as TextView
            posteddate = itemView.findViewById<View>(R.id.date) as TextView
            image = itemView.findViewById<View>(R.id.imageview_chat) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            mLayoutInflater.inflate(R.layout.post_history_items, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.setText(items[position].uid)
        holder.postbody.setText(items[position].postbody)
        holder.posteddate.setText(items[position].posteddate)
        Picasso.get().load("https://robohash.org/accusantiumvitaedolorem.png?size=300x300").into(holder.image)

        if(FirebaseAuth.getInstance().uid == items[position].uid) {
            holder.postbody.setTextColor(R.color.white)
        }
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