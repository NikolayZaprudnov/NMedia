package ru.netology.nmedia.viewmodel

import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            authorName.text = post.author
            val avatarUrl = "http://10.0.2.2:9999/avatars/${post.authorAvatar}"
            Glide.with(avatar)
                .load(avatarUrl)
                .fitCenter()
                .placeholder(R.drawable.ic_baseline_load_face_100)
                .error(R.drawable.ic_baseline_error_100)
                .timeout(10_000)
                .into(avatar)
            time.text = post.published.toString()
            content.text = post.content
            likes.isChecked = post.likedByMe
            likes.text = converter(post.likes)
            val attachmentUrl = "http://10.0.2.2:9999/media/${post.attachment?.url}"
            if (post.attachment != null) {
                videoGroup.visibility = View.VISIBLE
                Glide.with(attachmentImage)
                    .load(attachmentUrl)
                    .fitCenter()
                    .timeout(10_000)
                    .error(R.drawable.ic_baseline_error_100)
                    .into(attachmentImage)
                url.text = post.attachment?.description

            }

//            reposts.text = converter(post.repostAmount)
//            url.text = post.video

            root.setOnClickListener {
                onInteractionListener.onOpen(post)
            }
            likes.setOnClickListener {
                onInteractionListener.onLike(post)
            }
//            root.setOnClickListener {
//                onInteractionListener.onRoot(post)
//            }
            reposts.setOnClickListener {
                onInteractionListener.onRepost(post)

            }
            attachmentImage.setOnClickListener {
//
                onInteractionListener.onPlay(post)
            }
//            play.setOnClickListener {
//                onInteractionListener.onPlay(post)
//
//            }
            menu.isVisible = post.ownedByMe
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.option_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
//                                findNavController(FeedFragment).navigate(R.id.action_feedFragment_to_editPostFragment)
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
        var convert = when (amount) {
            in 0..999 -> amount.toString()
            in 1000..999999 -> ((amount / 1000).toString() + "k")
            else -> ((amount / 1000000).toString() + "M")
        }
        return convert
    }
}