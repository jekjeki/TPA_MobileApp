package edu.bluejack22_2.Caddo.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SuccessTransactionViewModel:ViewModel() {

    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    var getEmail = ""
    var baseTrId = ""

    fun getCurrentUser(){
        val user = Firebase.auth.currentUser

        user?.let {
            getEmail = it.email.toString()
        }
    }


    // fun get all base transaction user
    fun getBaseTransactionId(){
        db.collection("users")
            .whereEqualTo("UserEmail", getEmail)
            .get()
            .addOnSuccessListener { documents->
                for(document in documents){
                    baseTrId = "${document.data["UserBaseTransactionId"].toString()}"
                }
                Log.d("get-baseid-success","$baseTrId")
            }
    }

}