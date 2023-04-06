package ru.netology.nmedia.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.*
import ru.netology.nmedia.entity.toDto
import ru.netology.nmedia.entity.toEntity
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.error.AppError

class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {

    override val data = postDao.getAll()
        .map(List<PostEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override fun getNewer(id: Long): Flow<Int> = flow {
        while (true) {
            delay(10_000L)
            val response = PostsApi.retrofitService.getNewer(id)
            val posts = response.body().orEmpty()
            postDao.insert(posts.toEntity())
            emit(posts.size)
        }
    }
        .catch { e -> throw AppError.from(e) }
        .flowOn(Dispatchers.Default)


    override suspend fun getAllAsynch() {
        val response = PostsApi.retrofitService.getAll()
        if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
        response.body() ?: throw RuntimeException("Body is null")
        postDao.insert(response.body()!!.map { PostEntity.fromDto(it) })

    }

    override suspend fun likeById(id: Long) {
        postDao.likeById(id)
        val response = PostsApi.retrofitService.unlikeById(id)
        if (!response.isSuccessful) {
            postDao.unlikeById(id)
        }
    }

    override suspend fun unlikeById(id: Long) {
        postDao.unlikeById(id)
        val response = PostsApi.retrofitService.unlikeById(id)
        if (!response.isSuccessful) {
            postDao.likeById(id)
        };
    }

    override fun times() {
        TODO("Not yet implemented")
    }

    override suspend fun repostById(id: Long) {
        postDao.repostById(id)
        val response = PostsApi.retrofitService.repostById(id)
        if (!response.isSuccessful) {
            postDao.unrepostById(id)
        }
    }

    override suspend fun save(postS: Post) {
        val response = PostsApi.retrofitService.save(postS)
        if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
        val body = response.body() ?: throw RuntimeException("Body is null")
        postDao.insert(PostEntity.fromDto(body))
    }

    override suspend fun removeById(id: Long) {
        val response = PostsApi.retrofitService.removeById(id)
        if (response.isSuccessful) {
            postDao.removeById(id)
        }; throw RuntimeException("API SERVICE ERROR")
    }
}

