package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val authorName: String,
    val time: String,
    val content: String ,
    var likedByMe: Boolean,
    var likesAmount: Int,
    var repostAmount: Int
)
