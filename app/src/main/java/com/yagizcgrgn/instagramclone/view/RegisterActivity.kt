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

    private var intent : Intent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        intent  =  Intent(this@RegisterActivity,MainActivity::class.java)
        val view = binding.root


        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    fun registerSave(view: View){
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            binding.btRegisterSave.isEnabled = false
        }
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@RegisterActivity) { task ->
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
                        TODO("Snackbar add(optional)")
                    }
                }
        }catch (e : Exception){
            print(e.stackTrace)
        }

    }

    fun cancel(view: View){
        startActivity(intent)
        finish()
    }

}