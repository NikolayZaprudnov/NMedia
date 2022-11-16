package ru.netology.nmedia

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import java.time.LocalDateTime
import java.time.LocalTime
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        fun converter(amount: Int): String {
//            var convert =  when(amount){
//                in 0..999 -> amount.toString()
//                in 1000..999999 -> ((amount/1000).toString() + "k")
//                else -> ((amount/1000000).toString() + "M")
//            }
//            return convert
//        }
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                authorName.text = post.authorName
                time.text = post.time
                content.text = post.content
                likes.setImageResource(if(post.likedByMe)R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_likes)
                amounLikes.text = viewModel.converter(post.likesAmount)
                amountReposts.text = viewModel.converter(post.repostAmount)
                likes.setOnClickListener {
                    //if(post.likedByMe) post.likesAmount -- else post.likesAmount ++
                    viewModel.like()
                    amounLikes.text = viewModel.converter(post.likesAmount)
                }
                root.setOnClickListener {
                    viewModel.times()
                }
                reposts.setOnClickListener {
                    post.repostAmount++
                    amountReposts.text = viewModel.converter(post.repostAmount)

                }
            }
        }

        }
    }