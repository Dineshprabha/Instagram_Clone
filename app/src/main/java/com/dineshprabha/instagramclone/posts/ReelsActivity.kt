package com.dineshprabha.instagramclone.posts

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dineshprabha.instagramclone.HomeActivity
import com.dineshprabha.instagramclone.databinding.ActivityReelsBinding
import com.dineshprabha.instagramclone.model.Reel
import com.dineshprabha.instagramclone.model.User
import com.dineshprabha.instagramclone.utils.REEL
import com.dineshprabha.instagramclone.utils.REEL_FOLDER
import com.dineshprabha.instagramclone.utils.USER_NODE
import com.dineshprabha.instagramclone.utils.uploadVideo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ReelsActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl:String
    private lateinit var progressDialog:ProgressDialog

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog) {
                    url->
                if (url != null) {
                    videoUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        binding.SelectReel.setOnClickListener {
            launcher.launch("video/*")
        }


        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user:User = it.toObject<User>()!!
                    val reel: Reel = Reel(videoUrl!!, binding.etCaption.editText?.text.toString(), user.image!!)

                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document().set(reel).addOnSuccessListener {
                            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                            finish()
                        }
                    }
                }

        }
    }
}