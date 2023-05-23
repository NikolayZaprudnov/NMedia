package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewHolder

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onRepost(post: Post) {}
    fun onRoot(post: Post) {}
    fun onRemove(post: Post) {}
    fun onEdit(post: Post) {}
    fun onPlay(post: Post)
    fun onOpen(post: Post)
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : PagingDataAdapter<Post, PostViewHolder>(PostDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position) ?: return
        holder.bind(post)
    }
}

class PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}


