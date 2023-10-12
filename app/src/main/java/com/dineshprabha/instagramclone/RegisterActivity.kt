package com.dineshprabha.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.dineshprabha.instagramclone.databinding.ActivityRegisterBinding
import com.dineshprabha.instagramclone.model.User
import com.dineshprabha.instagramclone.utils.USER_NODE
import com.dineshprabha.instagramclone.utils.USER_PROFILE_FOLDER
import com.dineshprabha.instagramclone.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class RegisterActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it != null) {
                    user.image = it
                    binding.imgUserPhoto.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val text =
            "<font color=#FF000000>Already have an account?</font> <font color=#2D91E4>Login</font>"
        binding.txtLogin.setText(Html.fromHtml(text))

        user = User()
        window.statusBarColor = Color.TRANSPARENT


        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {
                binding.btnRegister.text = "Update Profile"

                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!

                        if (!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.imgUserPhoto)
                        }
                        binding.etName.editText?.setText(user.name)
                        binding.etEmail.editText?.setText(user.email)
                        binding.etPassword.editText?.setText(user.password)

                    }
            }
        }

        binding.btnRegister.setOnClickListener {
            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            startActivity(
                                Intent(
                                    this@RegisterActivity,
                                    HomeActivity::class.java
                                )
                            )
                            finish()
                        }
                }
            } else {
                if (binding.etName.editText?.text.toString().equals("") or
                    binding.etEmail.editText?.text.toString().equals("") or
                    binding.etPassword.editText?.text.toString().equals("")
                ) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please fill the details..",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.etEmail.editText?.text.toString(),
                        binding.etPassword.editText?.text.toString()
                    ).addOnCompleteListener { result ->

                        if (result.isSuccessful) {
                            user.name = binding.etName.editText?.text.toString()
                            user.email = binding.etEmail.editText?.text.toString()
                            user.password = binding.etPassword.editText?.text.toString()

                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Registered Successfully...",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(
                                        Intent(
                                            this@RegisterActivity,
                                            HomeActivity::class.java
                                        )
                                    )
                                    finish()
                                }

                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                result.exception?.localizedMessage, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.imgUserPhoto.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

    }
}