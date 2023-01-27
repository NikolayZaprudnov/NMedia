package ru.netology.nmedia.dto

import java.net.URL

data class Post(
    val id: Long,
    val authorName: String,
    val authorAvatar: String,
    val time: String,
    val content: String,
    val likedByMe: Boolean,
    var likesAmount: Int,
    var repostAmount: Int,
    val video: String?
)
