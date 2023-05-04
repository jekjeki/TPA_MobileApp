package edu.bluejack22_2.Caddo.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UpdateProfileViewModel:ViewModel() {

    //update user data
    fun updateUserData(id : String, address : String, email : String, name : String, password : String){

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        val data = hashMapOf(
            "UserAddress" to address,
            "UserEmail" to email,
            "UserName" to name,
            "UserPassword" to password
        )

        db.collection("users").document(id)
            .set(data)
            .addOnSuccessListener {
                Log.d("update-user", "updated")
            }
    }


    // update user data authentication
    fun updateAuthData(email : String){
        val user = Firebase.auth.currentUser

        user!!.updateEmail(email).addOnCompleteListener { task->
            if(task.isSuccessful){
                Log.d("auth-update", "email updated !")
            }
        }
    }


    // check name
    fun checkName(name : String):Int{

        var flag = 0

        if(name.length < 5)
        {
            flag++
        }

        return flag
    }


    fun checkEmail(email : String) : Int{

        var flag = 0

        if( !( email.endsWith(".com") || email.endsWith(".co.id")) ){
            flag++
        }

        return flag
    }

}
