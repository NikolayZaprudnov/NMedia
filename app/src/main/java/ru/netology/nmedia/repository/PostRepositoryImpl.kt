package ru.netology.nmedia.repository

import androidx.lifecycle.map
import retrofit2.Call
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import retrofit2.Callback
import retrofit2.Response
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

    override suspend fun likeById(id: Long): Boolean {
        val response = PostsApi.retrofitService.unlikeById(id)
        if (!response.isSuccessful){ return false };  return true
    }

    override suspend fun unlikeById(id: Long): Boolean {
        val response = PostsApi.retrofitService.unlikeById(id)
        if (!response.isSuccessful){ return false };  return true
    }

    override fun times() {
        TODO("Not yet implemented")
    }

    override suspend fun repostById(id: Long): Boolean {
        val response = PostsApi.retrofitService.repostById(id)
        if (!response.isSuccessful){ return false };  return true
    }

    override suspend fun save(postS: Post) {
        val response = PostsApi.retrofitService.save(postS)
        if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
        response.body() ?: throw RuntimeException("Body is null")
        postDao.insert(PostEntity.fromDto(response.body()!!))
    }
    override suspend fun removeById(id: Long): Boolean {
        val response = PostsApi.retrofitService.removeById(id)
        if (!response.isSuccessful){ return false };  return true
    }
}