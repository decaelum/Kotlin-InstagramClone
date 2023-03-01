package com.yagizcgrgn.instagramclone.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.yagizcgrgn.instagramclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        auth = Firebase.auth

        val currentUser = auth.currentUser
        TODO("Reminder button will add")
        setContentView(view)

    }
    fun login(view: View) {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()){
            binding.btLogin.isEnabled = false
        }
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Log.d("LOGIN", "Login successful")
                        val intent = Intent(this@MainActivity,FeedActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("LOGIN","Login Failed")
                        Toast.makeText(this@MainActivity,"Try Again",Toast.LENGTH_LONG).show()
                        binding.edtPassword.setText("")
                    }
                }
        }catch (e : Exception){
            print(e.stackTrace)
        }

    }

    fun register(view: View){
        val intent = Intent(this@MainActivity,RegisterActivity::class.java)
        startActivity(intent)
    }
}