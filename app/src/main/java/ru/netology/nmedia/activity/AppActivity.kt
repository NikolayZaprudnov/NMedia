package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.New.Companion.textArg

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
//        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank() != true) {
                return@let}
            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(androidx.navigation.fragment.R.id.nav_host_fragment_container).navigate(R.id.action_feedFragment_to_newPostFragment, Bundle().apply {
                textArg = text
            })
            }
//            else {
//                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }
        }

