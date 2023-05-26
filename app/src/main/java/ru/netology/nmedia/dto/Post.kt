package ru.netology.nmedia.dto

import ru.netology.nmedia.enumeration.AttachmentType

sealed interface FeedItem  {
    val id: Long

}


data class Post(
    override val id: Long,
    val author: String,
    val authorAvatar: String = "",
    val content: String,
    val published: Long,
    val likedByMe: Boolean,
    val likes: Int = 0,
    var attachment: Attachment? = null,
    var hidden: Boolean = false,
    val authorId: Long,
    val ownedByMe: Boolean = false,

    ): FeedItem

data class Ad(
    override val id: Long,
    val image: String,
) : FeedItem

data class Attachment(
    val url: String,
    val description: String?,
    val type: AttachmentType,
)