package ru.netology.nmedia.dto

import ru.netology.nmedia.enumeration.AttachmentType
import java.net.URL

//data class Post(
//    val id: Long,
//    val authorName: String,
//    val authorAvatar: String,
//    val time: String,
//    val content: String,
//    val likedByMe: Boolean,
//    var likesAmount: Int,
//    var repostAmount: Int,
//    val video: String?,
//    var hidden: Boolean = false
//)
data class Post(
    val id: Long,
    val author: String,
    val authorAvatar: String = "",
    val content: String,
    val published: Long,
    val likedByMe: Boolean,
    val likes: Int = 0,
    var attachment: Attachment? = null,
    var hidden: Boolean = false
)

data class Attachment(
    val url: String,
    val description: String?,
    val type: AttachmentType,
)