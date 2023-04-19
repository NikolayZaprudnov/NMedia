package ru.netology.nmedia.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Field
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.api.PostsApiService
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.*
import ru.netology.nmedia.entity.toDto
import ru.netology.nmedia.entity.toEntity
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Attachment
import ru.netology.nmedia.dto.Media
import ru.netology.nmedia.dto.User
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.enumeration.AttachmentType
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.AppError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.model.PhotoModel
import java.io.IOException

class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {

    override val data = postDao.getAll()
        .map(List<PostEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override fun getNewer(id: Long): Flow<Int> = flow {
        while (true) {
            delay(10_000L)
            val response = PostsApi.retrofitService.getNewer(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val posts = response.body().orEmpty()
            postDao.insert(posts.toEntity().map {
                it.copy(hidden = true)
            })
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
//        postDao.repostById(id)
//        val response = PostsApi.retrofitService.repostById(id)
//        if (!response.isSuccessful) {
//            postDao.unrepostById(id)
//        }
    }

    override suspend fun showAll() {
        postDao.showAll()
    }

    override suspend fun save(postS: Post) {
        try {
            val response = PostsApi.retrofitService.save(postS)
            if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
            val body = response.body() ?: throw RuntimeException("Body is null")
            postDao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        }
    }


    override suspend fun saveWithAttachment(postS: Post, photoModel: PhotoModel) {
        try {
            val media = upload(photoModel)
            val response = PostsApi.retrofitService.save(postS.copy(
                attachment = Attachment(media.id,"",  AttachmentType.IMAGE)
            ))
            if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
            val body = response.body() ?: throw RuntimeException("Body is null")
            postDao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        }
    }
    override suspend fun updateUser(login: String, pass:String){
        val response = PostsApi.authRetrofitServise.updateUser(login, pass)
        if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
        val userId = response.body()!!.id
        val userToken = response.body()?.token
        AppAuth.getInstance().setAuth(userId,userToken)
    }

    override suspend fun registerUser(login: String, pass: String, name: String) {
        val response = PostsApi.authRetrofitServise.registerUser(login, pass, name)
        if (!response.isSuccessful) throw RuntimeException("API SERVICE ERROR")
        val userId = response.body()!!.id
        val userToken = response.body()?.token
        AppAuth.getInstance().setAuth(userId,userToken)
    }

    override suspend fun registerWithPhoto(
        login: String,
        pass: String,
        name: String,
        avatar: PhotoModel,
    ) {
        val response = PostsApi.authRetrofitServise.registerWithPhoto(
            login.toRequestBody("login".toMediaType()),
            pass.toRequestBody("pass".toMediaType()),
            name.toRequestBody("name".toMediaType()),
            MultipartBody.Part.createFormData("file", avatar.file.name, avatar.file.asRequestBody()),
        )
        val userId = response.body()!!.id
        val userToken = response.body()?.token
        AppAuth.getInstance().setAuth(userId,userToken)
    }

    private suspend fun upload(photo: PhotoModel): Media {
        val response = PostsApi.mediaRetrofitService.uploadPhoto(
            MultipartBody.Part.createFormData("file", photo.file.name, photo.file.asRequestBody())
        )
        return response.body() ?: throw  RuntimeException("Body is null")
    }

    override suspend fun removeById(id: Long) {
        val response = PostsApi.retrofitService.removeById(id)
        if (response.isSuccessful) {
            postDao.removeById(id)
        }; throw RuntimeException("API SERVICE ERROR")
    }
}

