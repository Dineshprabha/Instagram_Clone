package com.dineshprabha.instagramclone.posts

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dineshprabha.instagramclone.HomeActivity
import com.dineshprabha.instagramclone.databinding.ActivityPostBinding
import com.dineshprabha.instagramclone.model.Post
import com.dineshprabha.instagramclone.model.User
import com.dineshprabha.instagramclone.utils.POST
import com.dineshprabha.instagramclone.utils.POST_FOLDER
import com.dineshprabha.instagramclone.utils.USER_NODE
import com.dineshprabha.instagramclone.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl:String ?= null

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) {
                url->
                if (url != null) {
                    binding.imgPost.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.imgPost.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user = it.toObject<User>()!!

                val post: Post = Post(
                    postUrl = imageUrl!!,
                    caption = binding.etCaption.editText?.text.toString(),
                    name = user.name,
                    timer = System.currentTimeMillis().toString()
                )

                    Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }
                    }
            }

        }
    }
}