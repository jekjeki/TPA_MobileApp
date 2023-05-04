package edu.bluejack22_2.Caddo.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import edu.bluejack22_2.Caddo.Model.User

class ProfileViewModel : ViewModel() {


    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var getEmail = ""
    var currName = ""



    fun passNameToFrag(): String {
        return "$currName"
    }

    // get current user data
    fun getCurrentUserProfileData(){

        val user = Firebase.auth.currentUser

        user?.let {
            val email = it.email
            getEmail = email.toString()
        }
    }

}