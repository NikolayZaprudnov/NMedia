package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import util.StringArg

class New : Fragment() {
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
//        super.onCreate(savedInstanceState)
//        val binding = ActivityNewPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        binding.editText.requestFocus()
        val viewModel: PostViewModel by viewModels()
        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.editText.text.toString())
            viewModel.save()
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