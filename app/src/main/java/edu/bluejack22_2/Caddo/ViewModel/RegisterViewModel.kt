package edu.bluejack22_2.Caddo.ViewModel

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class RegisterViewModel:ViewModel() {

    private var auth :FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore


    fun createNewUser(email : String, password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Log.d("email", email)
                }
                else{

                }
            }
    }

    fun insertUserData(name : String, email: String, address:String, password:String){

        // generated id for transaction
        var a = (0..9).random()
        var b = (0..9).random()
        var c = (0..9).random()

        var trId = "TR$a$b$c"


        val data = hashMapOf(
            "UserName" to name,
            "UserEmail" to email,
            "UserAddress" to address,
            "UserPassword" to password,
            "UserBaseTransactionId" to trId
        )

        db.collection("users")
            .add(data)
            .addOnSuccessListener { documentRef ->
                Log.d("success", "id:${documentRef.id}")
            }
            .addOnFailureListener {
                e->Log.d("fail", "error", e)
            }
    }

}