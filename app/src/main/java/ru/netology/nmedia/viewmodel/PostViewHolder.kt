package ru.netology.nmedia.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity.apply
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.createDeviceProtectedStorageContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat.apply
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter

import ru.netology.nmedia.viewmodel.PostViewModel
import android.widget.Toast
import androidx.activity.result.launch
import androidx.core.content.ContextCompat

import ru.netology.nmedia.util.AndroidUtils
class PostViewHolder(
    private val binding: PostCardBinding,
   private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            authorName.text = post.authorName
            time.text = post.time
            content.text = post.content
            likes.isChecked = post.likedByMe
            likes.text = converter(post.likesAmount)
            reposts.text = converter(post.repostAmount)
            url.text = post.video
            if (url.text.isNullOrEmpty()){
                videoGroup.visibility = View.GONE
            }
            likes.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            root.setOnClickListener {
                onInteractionListener.onRoot(post)
            }
            reposts.setOnClickListener {
                onInteractionListener.onRepost(post)

            }
            video.setOnClickListener {
//
                onInteractionListener.onPlay(post)
            }
            play.setOnClickListener {
                onInteractionListener.onPlay(post)

            }
            menu.setOnClickListener {
                PopupMenu(it.context , it).apply{
                    inflate(R.menu.option_post)
                    setOnMenuItemClickListener { item ->
                        when(item.itemId){
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit ->{
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }


    fun converter(amount: Int): String {
        var convert =  when(amount){
            in 0..999 -> amount.toString()
            in 1000..999999 -> ((amount/1000).toString() + "k")
            else -> ((amount/1000000).toString() + "M")
        }
        return convert
    }
}