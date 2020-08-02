package com.perqin.andori.data.models

import com.google.gson.annotations.SerializedName

// FIXME: Replace SuccessfulStationResponse with better parsing approach
data class StationResponse(
    val status: String
)

data class SuccessfulStationResponse(
    val response: List<RoomData>
)

data class FailedStationResponse(
    val response: String
)

//number	string	房间号
//raw_message	string	房间的说明文字/原始信息
//source_info	object	数据来源信息
//type	string	房间类型
//time	number	房间的发布时间，13位时间戳
//user_info	object	用户信息
data class RoomData(
    val number: String,
    @SerializedName("raw_message")
    val rawMessage: String,
    @SerializedName("source_info")
    val sourceInfo: SourceInfo,
    val type: String,
    val time: Long,
    @SerializedName("user_info")
    val userInfo: UserInfo,
)

//name	string	数据源名称
//type	string	数据源类型
data class SourceInfo(
    val name: String,
    val type: String,
)

//type	string	用户类型
//user_id	number	用户ID
//username	string	用户名
//avatar	string	用户头像
data class UserInfo(
    val type: String,
    @SerializedName("user_id")
    val userId: Long,
    val username: String,
    val avatar: String,
)
