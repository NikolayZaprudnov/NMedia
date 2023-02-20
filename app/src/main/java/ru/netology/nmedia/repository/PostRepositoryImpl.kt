package ru.netology.nmedia.repository

import androidx.lifecycle.map
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {

    override  fun data() = postDao.getAll().map { it.map(PostEntity::toDto) }

    override suspend fun getAllAsynch() {
        val response = PostsApi.retrofitService.getAll()
        if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
        response.body() ?: throw RuntimeException("Body is null")
        postDao.insert(response.body()!!.map { PostEntity.fromDto(it) })

    }

    override suspend fun likeById(id: Long){
        postDao.likeById(id)
        val response = PostsApi.retrofitService.unlikeById(id)
        if (!response.isSuccessful){ postDao.unlikeById(id) }
    }

    override suspend fun unlikeById(id: Long) {
        postDao.unlikeById(id)
        val response = PostsApi.retrofitService.unlikeById(id)
        if (!response.isSuccessful){ postDao.likeById(id) };
    }

    override fun times() {
        TODO("Not yet implemented")
    }

    override suspend fun repostById(id: Long) {
        postDao.repostById(id)
        val response = PostsApi.retrofitService.repostById(id)
        if (!response.isSuccessful){ postDao.unrepostById(id)}
    }

    override suspend fun save(postS: Post) {
        val response = PostsApi.retrofitService.save(postS)
        if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
        val body = response.body() ?: throw RuntimeException("Body is null")
        postDao.insert(PostEntity.fromDto(body))
    }

    override suspend fun removeById(id: Long){
        val response = PostsApi.retrofitService.removeById(id)
        if (response.isSuccessful){ postDao.removeById(id)}; throw RuntimeException("API SERVICE ERROR")
    }
}