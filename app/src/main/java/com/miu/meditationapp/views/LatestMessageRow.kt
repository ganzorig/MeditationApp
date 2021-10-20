package com.miu.meditationapp.views

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miu.meditationapp.R
import com.miu.meditationapp.models.ChatMessage
import com.miu.meditationapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*
import kotlinx.android.synthetic.main.*


class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>() {
  var chatPartnerUser: User? = null

  override fun bind(viewHolder: ViewHolder, position: Int) {
    viewHolder.itemView.message_textview_latest_message.text = chatMessage.text

    val chatPartnerId: String
    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
      chatPartnerId = chatMessage.toId
    } else {
      chatPartnerId = chatMessage.fromId
    }

    val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
    ref.addListenerForSingleValueEvent(object: ValueEventListener {
      override fun onDataChange(p0: DataSnapshot) {
        chatPartnerUser = p0.getValue(User::class.java)
        viewHolder.itemView.username_textview_latest_message.text = chatPartnerUser?.username

        val targetImageView = viewHolder.itemView.imageview_latest_message
        Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)
      }

      override fun onCancelled(p0: DatabaseError) {

      }
    })
  }

  override fun getLayout(): Int {
    return R.layout.latest_message_row
  }
}