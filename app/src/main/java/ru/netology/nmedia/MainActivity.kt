package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import android.os.Build
import androidx.annotation.RequiresApi




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter (
            {viewModel.likeById(it.id)},
            {viewModel.times()},
            {viewModel.repostById(it.id)}
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this) { post ->
            adapter.submitList(post)
        }
    }
}
  //      viewModel.data.observe(this) { post ->
 //           post.map { post ->
 //               PostCardBinding.inflate(layoutInflater, binding.container, true).apply {
//                    authorName.text = post.authorName
//                    time.text = post.time
//                    content.text = post.content
//                    likes.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_likes)
//                    amounLikes.text = viewModel.converter(post.likesAmount)
//                    amountReposts.text = viewModel.converter(post.repostAmount)
//                    likes.setOnClickListener {
//                        viewModel.likeById(post.id)
//                        amounLikes.text = viewModel.converter(post.likesAmount)
//                        root.setOnClickListener {
//                            viewModel.times()
//                        }
//                        reposts.setOnClickListener {
//                            post.repostAmount++
//                            amountReposts.text = viewModel.converter(post.repostAmount)
//
//                        }
//                    }
// //}