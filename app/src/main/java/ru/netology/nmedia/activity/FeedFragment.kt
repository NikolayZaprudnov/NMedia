package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.AuthViewModel


class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private val authViewModel: AuthViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )


        val adapter = PostsAdapter(object : OnInteractionListener {


            override fun onLike(post: Post) {
                if (post.likedByMe == false) {
                    viewModel.likeById(post.id)
                } else {
                    viewModel.unlikeById(post.id)
                }
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

            override fun onOpen(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_onePostFragment,
                    Bundle().apply {
                        val idArg = putLong("id", post.id)
                    })
            }


            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onPlay(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_imageFragment,
                    Bundle().apply {
                        textArg = post.attachment?.url
                    })
//                val startVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                startActivity(startVideo)
            }
        })
        binding.list.adapter = adapter
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }

        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.emptyText.isVisible = state.empty
        }
        viewModel.newerCount.observe(viewLifecycleOwner) { state ->
            binding.freshPosts.isVisible = state > 0
        }
        binding.freshPosts.setOnClickListener {
            viewModel.showAll()
            var position = (binding.list.scrollState)
            binding.list.smoothScrollToPosition(position)
            binding.freshPosts.isVisible = false
        }

        binding.refresh.setOnRefreshListener {
            viewModel.showAll()
            viewModel.loadPosts()
            binding.refresh.isRefreshing = false
        }


        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            findNavController().navigate(R.id.action_feedFragment_to_editPostFragment,
                Bundle().apply {
                    textArg = post.content
                    arguments = bundleOf(
                        "authorId" to post.author,
                        "content" to post.content
                    )
                })
        }
        val draftText = arguments?.textArg.toString()
        binding.newpost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
            Bundle().apply {
                textArg = draftText
            }
        }

        authViewModel.state.observe(viewLifecycleOwner){ authState ->

        var menuProvider: MenuProvider? = null
        requireActivity().addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuProvider?.let { requireActivity().removeMenuProvider(it) }
                menuInflater.inflate(R.menu.main_menu, menu)
                menu.setGroupVisible(R.id.authorized, authViewModel.authorized)
                menu.setGroupVisible(R.id.unAuthorized, !authViewModel.authorized)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.signOut -> {
                        AppAuth.getInstance().clear()
                        true
                    }
                    R.id.signIn -> {
                        findNavController().navigate(R.id.action_feedFragment_to_signInFragment)
                        true
                    }
                    R.id.signUp -> {
                        findNavController().navigate(R.id.action_feedFragment_to_registrationFragment)
                        true
                    } else -> false
                }
            }

        }.apply { menuProvider = this })}

        return binding.root
    }
}

