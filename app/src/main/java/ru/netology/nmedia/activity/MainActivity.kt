package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.core.content.ContextCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()

        val newPostLauncher = registerForActivityResult(NewPostResultContract()){
                result -> result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
               val intent = Intent().apply {
                   action = Intent.ACTION_EDIT
                   putExtra(Intent.EXTRA_TEXT, post.content)
                   type = "text/plain"
               }
                startActivity(intent)
                viewModel.edit(post)
            }
            val editPostLauncher = registerForActivityResult(EditPostResultContract()){
                result -> result ?: return@registerForActivityResult
                viewModel.edit(result)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRepost(post: Post) {
                viewModel.repostById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "Repost post")
                startActivity(shareIntent)
            }



            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(post.content)
            }
        }
        binding.notEdit.setOnClickListener {
            with(binding.content){
                if (text.isNotEmpty()){
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.notEdit),
                        Toast.LENGTH_SHORT
                    ).show()
                    text.clear()
                    binding.editGroup.visibility = View.GONE
                    AndroidUtils.hideKeyboard(this)
                }
            }

        }
        binding.newpost.setOnClickListener{
            newPostLauncher.launch()
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                binding.editGroup.visibility = View.GONE
                AndroidUtils.hideKeyboard(this)
            }
        }

    }
}

