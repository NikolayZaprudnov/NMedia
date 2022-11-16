package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val authorName: String,
    val time: String,
    val content: String,
    val likedByMe: Boolean,
    var likesAmount: Int,
    var repostAmount: Int
)
