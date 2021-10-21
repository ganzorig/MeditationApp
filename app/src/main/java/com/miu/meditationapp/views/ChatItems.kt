package com.miu.meditationapp.views

import com.miu.meditationapp.R
import com.miu.meditationapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*


class ChatFromItem(val text: String, val user: User): Item<ViewHolder>() {
  override fun bind(viewHolder: ViewHolder, position: Int) {
    viewHolder.itemView.textview_from_row.text = text

    val uri = user.profileImageUrl
    val targetImageView = viewHolder.itemView.imageview_chat_from_row
    Picasso.get().load(uri).into(targetImageView)
  }

  override fun getLayout(): Int {
    return R.layout.chat_from_row
  }
}

class ChatToItem(val text: String, val user: User): Item<ViewHolder>() {
  override fun bind(viewHolder: ViewHolder, position: Int) {
    viewHolder.itemView.textview_to_row.text = text

    // load our user image into the star
    val uri = user.profileImageUrl
    val targetImageView = viewHolder.itemView.imageview_chat_to_row
    Picasso.get().load(uri).into(targetImageView)
  }

  override fun getLayout(): Int {
    return R.layout.chat_to_row
  }
}