package com.yagizcgrgn.instagramclone.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

import com.yagizcgrgn.instagramclone.databinding.ActivityUploadBinding
import java.util.*

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore : FirebaseFirestore


    private var selectedPicture : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore

        registerLaıncher()
    }

    fun share(view: View) {
        val storage = Firebase.storage
        //universal unique id
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpeg"

        val reference = storage.reference
        val imageReference = reference.child("images").child(imageName)
        selectedPicture?.let { reference.putFile(it)
            .addOnSuccessListener {
                //download url -> firestore
                val uploadPictureReferance = storage.reference.child("images").child(imageName)
                uploadPictureReferance.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()

                    val postMap = hashMapOf<String, Any>()
                    postMap.put("downloadUrl",downloadUrl)
                    postMap.put("userEmail", auth.currentUser!!.email!!)
                    postMap.put("comment", binding.edtComment.text.toString())
                    postMap.put("date",Timestamp.now())

                    firestore.collection("Posts").add(postMap).addOnSuccessListener {
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this@UploadActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
                    }

                }.addOnFailureListener{
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            } }
            ?.addOnFailureListener{
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }

    }

    fun selectImage(view: View) {
        //Android 33+ -> READ_MEDIA_IMAGES
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_MEDIA_IMAGES)) {
                    //rationale
                    Snackbar.make(view, "Permission Needed!!!", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", View.OnClickListener {
                            //request permission
                            permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                        }).show()
                } else {
                    //request permission
                    permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                //intent
                activityResultLauncher.launch(intentToGallery)
            }
        } else {
            //Android 32- -> READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    //rationale
                    Snackbar.make(view, "Permission Need!!!", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", View.OnClickListener {
                            //request permission
                            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        }).show()
                } else {
                    //request permission
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                //intent
                activityResultLauncher.launch(intentToGallery)
            }

        }


    }

    private fun registerLaıncher(){

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                val intentFromResult = result.data
                if(intentFromResult != null){
                   selectedPicture = intentFromResult.data
                    selectedPicture?.let {
                        binding.ivSelectImage.setImageURI(it)
                    }
                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }else{
                //permission denied
                Toast.makeText(this@UploadActivity,"Permission Needed!!!", Toast.LENGTH_LONG).show()
            }
        }
    }
}