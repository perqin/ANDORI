package com.perqin.andori.data

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.perqin.andori.data.models.RoomData
import com.perqin.andori.data.models.StationResponse
import com.perqin.andori.data.models.SuccessfulStationResponse
import okhttp3.*

private const val WS_URL = "wss://api.bandoristation.com:50443/"

object BandoriStationService : WebSocketListener() {
    private val gson = Gson()
    private val uiHandler = Handler(Looper.getMainLooper())
    private val client = OkHttpClient.Builder()
        .build()
    private var webSocket: WebSocket? = null
    private val heartbeatRunnable: Runnable = object : Runnable {
        override fun run() {
            webSocket?.send("heartbeat-andori")
            uiHandler.postDelayed(this, 1000 * 30)
        }
    }
    private val roomList = MutableLiveData<List<RoomData>>().apply {
        value = emptyList()
    }
    fun roomList(): LiveData<List<RoomData>> = roomList

    fun startConnection() {
        if (webSocket != null) {
            return
        }
        val request = Request.Builder()
            .url(WS_URL)
            .build()
        webSocket = client.newWebSocket(request, this)
    }

    fun stopConnection() {
        val webSocket = this.webSocket ?: return
        webSocket.close(1000, null)
        uiHandler.removeCallbacks(heartbeatRunnable)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        uiHandler.post(heartbeatRunnable)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("onMessage: $text")
        if (gson.fromJson(text, StationResponse::class.java).status == "success") {
            val response = gson.fromJson(text, SuccessfulStationResponse::class.java).response
            roomList.postValue(response.sortedByDescending { it.time } + roomList.value!!)
        }
    }
}
