package edu.bluejack22_2.Caddo.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel:ViewModel() {

    private var user = Firebase.auth.currentUser
    var getName : String = ""

    fun getCurrentUserName() {

        user?.let {
            val name = it.email
            getName = name.toString()
        }
    }

    fun getUserNameLength() : Int{
        var totalChar = 0
        for (i in 0..getName.length)
        {
            if(getName[i] == '@')
            {
                break
            }
            else{
                totalChar++
            }
        }
        return totalChar
    }

}