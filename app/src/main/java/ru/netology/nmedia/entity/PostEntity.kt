package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorName: String,
    val authorAvatar: String,
    val time: String,
    val content: String,
    val likedByMe: Boolean,
    var likesAmount: Int,
    var repostAmount: Int,
    val video: String?
){
    fun toDto() = Post(id, authorName, authorAvatar, time, content, likedByMe, likesAmount, repostAmount, video)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.authorName, dto.authorAvatar, dto.time, dto.content, dto.likedByMe, dto.likesAmount, dto.repostAmount, dto.video)
    }
}