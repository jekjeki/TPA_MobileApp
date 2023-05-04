package edu.bluejack22_2.Caddo.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FailedTransactionViewModel : ViewModel() {

    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    // data
    var email : String = ""
    var baseTrId : String = ""

    // get current email login
    fun getLoginData(){

        val user = Firebase.auth.currentUser

        user?.let {
            email = it.email.toString()
        }
    }

    // fun get current users data
    fun getCurrentLoginData(){
        db.collection("users")
            .whereEqualTo("UserEmail", email)
            .get()
            .addOnSuccessListener { documents->
                for(document in documents){
                    baseTrId = document.data["UserBaseTransactionId"].toString()
                }
            }
    }

}