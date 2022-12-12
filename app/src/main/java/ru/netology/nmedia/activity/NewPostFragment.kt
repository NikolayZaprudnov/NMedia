package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
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
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.textArg?.let(binding.editText::setText)

        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.editText.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }
}
//class NewPostFragment : Fragment() {
//    override fun onCreateView(nflater: LayoutInflater,
//                              container: ViewGroup?,
//                              savedInstanceState: Bundle?):View {
//
//        val binding = FragmentNewPostBinding.inflate
//        setContentView(binding.root)
//        binding.editText.requestFocus()
//        binding.ok.setOnClickListener{
//            viewModel.changeContent
////            val intent = Intent()
////            if( binding.editText.text.isBlank()){
////                setResult(Activity.RESULT_CANCELED, intent)
////            } else{
////
////            val content = binding.editText.text.toString()
////            intent.putExtra(Intent.EXTRA_TEXT, content)
////            setResult(Activity.RESULT_OK, intent)
////            }
////            finish()
//        }
//    }
//}