package com.perqin.andori.ui.pages.station

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.perqin.andori.R
import com.perqin.andori.data.BandoriStationService
import kotlinx.android.synthetic.main.fragment_station.*

class StationFragment : Fragment() {
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var adapter: RoomListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        adapter = RoomListRecyclerAdapter()
        adapter.onCopy = {
            clipboardManager.setPrimaryClip(ClipData.newPlainText(requireContext().getString(R.string.label_roomNumber), it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_station, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        roomListRecyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        BandoriStationService.roomList().observe(viewLifecycleOwner) {
            adapter.roomList = it
        }
    }
}
