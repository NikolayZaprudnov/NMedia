package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import util.StringArg

class NewPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        var draft:String? = arguments?.textArg.toString()
        val callback = requireActivity().onBackPressedDispatcher.
            addCallback(viewLifecycleOwner){
                draft = binding.editText.text.toString()
                findNavController().navigate(R.id.feedFragment,
                    Bundle().apply {
                        textArg = draft
                    }
                )
            }
//        super.onCreate(savedInstanceState)
//        val binding = ActivityNewPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        if (draft != null){
            binding.editText.setText(draft)
        }
        binding.editText.requestFocus()
        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.editText.text.toString())
            viewModel.save()
            draft = null
            findNavController().navigateUp()
        }
        return binding.root
    }}
//            val intent = Intent()
//            if( binding.editText.text.isBlank()){
//                setResult(Activity.RESULT_CANCELED, intent)
//            } else{
//
//            val content = binding.editText.text.toString()
//            intent.putExtra(Intent.EXTRA_TEXT, content)
//            setResult(Activity.RESULT_OK, intent)
//            }
////            finish()
//        }
//    }
//}