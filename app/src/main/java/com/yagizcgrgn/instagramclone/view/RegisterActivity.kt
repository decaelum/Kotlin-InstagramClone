package com.yagizcgrgn.instagramclone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yagizcgrgn.instagramclone.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        auth = FirebaseAuth.getInstance()
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    fun registerSave(view: View){
        val intent = Intent(this@RegisterActivity,MainActivity::class.java)
        val email = binding.edtEmail.toString()
        val password = binding.edtPassword.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            if (email.isEmpty() || password.isEmpty()){
                binding.btRegisterSave.isEnabled = false
            }
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("NEWUSER", "New user added")
                            val user = auth.currentUser
                            startActivity(intent)
                            finish()
                        } else {
                            Log.d("NEWUSER", "New user adding failed", task.exception)
                            Toast.makeText(this@RegisterActivity, "Adding Failed", Toast.LENGTH_LONG
                            ).show()
                            startActivity(intent)
                            finish()
                        }
                    }
            }catch (e : Exception){
                print(e.stackTrace)
            }
        }


    }

    fun cancel(view: View){

    }

}