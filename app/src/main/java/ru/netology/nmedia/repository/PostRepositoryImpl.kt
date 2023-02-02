package ru.netology.nmedia.repository

import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import java.io.IOException
import java.util.concurrent.TimeUnit
import retrofit2.Callback
import retrofit2.Response

class PostRepositoryImpl : PostRepository {


    override fun getAllAsynch(callback: PostRepository.Callback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                when {
                    !response.isSuccessful -> callback.onError(Exception(response.message()))
                    response.code() == 404 -> callback.onError(Exception("Error 404"))
                    response.body() == null -> callback.onError(Exception("body is null"))
                    else -> callback.onSucces(response.body() ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                callback.onError(Exception(t))
            }

        })
    }

    override fun likeById(id: Long, callback: PostRepository.CallbackFoPost) {
        PostsApi.retrofitService.likeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                callback.onSucces()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }

        })
    }

    override fun unlikeById(id: Long, callback: PostRepository.CallbackFoPost) {
        PostsApi.retrofitService.unlikeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                callback.onSucces()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }

    override fun times() {
        TODO("Not yet implemented")
    }

    override fun repostById(id: Long, callback: PostRepository.CallbackFoPost) {
        PostsApi.retrofitService.repostById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                callback.onSucces()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }

    override fun save(postS: Post, callback: PostRepository.CallbackFoPost) {
        PostsApi.retrofitService.save(postS).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                callback.onSucces()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }

    override fun removeById(id: Long, callback: PostRepository.CallbackFoPost) {
        PostsApi.retrofitService.removeById(id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                callback.onSucces()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }
        })
    }
}