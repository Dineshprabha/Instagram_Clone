package com.dineshprabha.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dineshprabha.instagramclone.databinding.ActivityLoginBinding
import com.dineshprabha.instagramclone.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCreateAnAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.editText?.text.toString().equals("") or
                binding.etPassword.editText?.text.toString().equals("")){
                Toast.makeText(
                    this@LoginActivity,
                    "Please fill the details..",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                var user=User(binding.etEmail.editText?.text.toString(), binding.etPassword.editText?.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(
                                this@LoginActivity,
                                it.exception?.localizedMessage, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}