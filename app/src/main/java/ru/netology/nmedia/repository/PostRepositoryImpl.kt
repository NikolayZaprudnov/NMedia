package ru.netology.nmedia.repository

import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.EMPTY_REQUEST
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import java.io.IOException
import java.util.concurrent.TimeUnit

class PostRepositoryImpl: PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }


    override fun getAllAsynch(callback: PostRepository.Callback<List<Post>>) {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow/posts")
            .build()
        client.newCall(request)
            .enqueue(object : Callback{
                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful){
                        callback.onError(Exception(response.message))
                        return
                    }
                    val body = response.body?.string()?: throw RuntimeException("body is null")
                    try {
                        callback.onSucces(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception){
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }

    override fun likeById(id: Long, callback: PostRepository.CallbackFoPost) {
          val request: Request = Request.Builder()
              .post(EMPTY_REQUEST)
              .url("${BASE_URL}/api/slow/posts/$id/likes")
              .build()

        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback.onSucces()
                }


            })
    }
    override fun unlikeById(id: Long, callback: PostRepository.CallbackFoPost) {
        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}/api/slow/posts/$id/likes")
            .build()

        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback.onSucces()
                }


            })
    }

    override fun times() {
        TODO("Not yet implemented")
    }

    override fun repostById(id: Long, callback: PostRepository.CallbackFoPost) {
        val request: Request = Request.Builder()
            .post(EMPTY_REQUEST)
            .url("${BASE_URL}/api/slow/posts/$id/repost")
            .build()

        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback.onSucces()
                }


            })
    }

    override fun save(postS: Post) {
        val request: Request = Request.Builder()
            .post(gson.toJson(postS).toRequestBody(jsonType))
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .execute()
            .close()
    }

    override fun removeById( id: Long, callback: PostRepository.CallbackFoPost) {
        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}/api/slow/posts/$id")
            .build()

        client.newCall(request)
            .enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                   callback.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback.onSucces()
                }


            })

    }
}