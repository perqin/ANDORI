package com.perqin.andori.ui.pages.station

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perqin.andori.R
import com.perqin.andori.data.models.RoomData
import kotlinx.android.synthetic.main.item_room.view.*

class RoomListRecyclerAdapter : RecyclerView.Adapter<RoomListRecyclerAdapter.ViewHolder>() {
    var roomList: List<RoomData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onCopy: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val roomData = roomList[position]
        holder.numberTextView.text = roomData.number
        holder.messageTextView.text = roomData.rawMessage
        holder.copyButton.setOnClickListener {
            onCopy?.invoke(roomData.number)
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberTextView: TextView = itemView.numberTextView
        val messageTextView: TextView = itemView.messageTextView
        val copyButton: ImageButton = itemView.copyButton
    }
}