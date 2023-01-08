package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import java.time.LocalDateTime

class PostRepositoryRoomImpl(
    private val dao: PostDao,
) : PostRepository {

    override fun getAll() = Transformations.map(dao.getAll()){list ->
        list.map {
            it.toDto()
        }

    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun times() {

    }

    override fun repostById(id: Long) {
        dao.repostById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}