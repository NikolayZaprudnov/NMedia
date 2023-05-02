//package ru.netology.nmedia.di
//
//import android.content.Context
//import androidx.room.Room
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.create
//import ru.netology.nmedia.BuildConfig
//import ru.netology.nmedia.api.AuthApiService
//import ru.netology.nmedia.api.MediaApiService
//import ru.netology.nmedia.api.PostsApiService
//import ru.netology.nmedia.auth.AppAuth
//import ru.netology.nmedia.db.AppDb
//import ru.netology.nmedia.repository.PostRepository
//import ru.netology.nmedia.repository.PostRepositoryImpl
//
//class DependecyContainer(
//    private val context: Context,
//) {
//    companion object {
//        private const val BASE_URL = "${BuildConfig.BASE_URL}/api/slow/"
//
//        @Volatile
//        private var instance: DependecyContainer? = null
//
//        fun initApp(context: Context) {
//            instance = DependecyContainer(context)
//        }
//
//        fun getInstance(): DependecyContainer {
//            return instance!!
//        }
//    }
//
//    private val logging = HttpLoggingInterceptor().apply {
//        if (BuildConfig.DEBUG) {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//    }
//
//    val appAuth = AppAuth(context)
//
//    private val postOkhttp = OkHttpClient.Builder()
//        .addInterceptor(logging)
//        .addInterceptor { chain ->
//            appAuth.authStateFlow.value.token?.let { token ->
//                val newRequest = chain.request().newBuilder()
//                    .addHeader("Authorization", token)
//                    .build()
//                return@addInterceptor chain.proceed(newRequest)
//            }
//            chain.proceed(chain.request())
//        }
//        .build()
//    private val mediaOkhttp = OkHttpClient.Builder()
//        .build()
//
//    private val postRetrofit = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl(BASE_URL)
//        .client(postOkhttp)
//        .build()
//    private val mediaRetrofit = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl(BASE_URL)
//        .client(mediaOkhttp)
//        .build()
//    private val authRetrofit = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl(BASE_URL)
//        .client(mediaOkhttp)
//        .build()
//
//    private val appBd = Room.databaseBuilder(context, AppDb::class.java, "app.db")
//        .fallbackToDestructiveMigration()
//        .build()
//
//    val apiService = postRetrofit.create<PostsApiService>()
//
//    private val authApiService = authRetrofit.create<AuthApiService>()
//
//    private val mediaApiService = mediaRetrofit.create<MediaApiService>()
//
//    private val postDao = appBd.postDao()
//
//    val repository: PostRepository = PostRepositoryImpl(
//        postDao,
//        apiService,
//        authApiService,
//        mediaApiService
//    )
//
//
//}