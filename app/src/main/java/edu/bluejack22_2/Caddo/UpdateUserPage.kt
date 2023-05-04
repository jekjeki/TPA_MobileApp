package edu.bluejack22_2.Caddo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_2.Caddo.ViewModel.UpdateProfileViewModel

class UpdateUserPage : AppCompatActivity() {

    private lateinit var etNameUpdate : EditText
    private lateinit var etEmailUpdate : EditText
    private lateinit var etAddressUpdate : EditText
    private lateinit var btnUpdate : Button

    private lateinit var updateProfileViewModel : UpdateProfileViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_page)

        updateProfileViewModel = ViewModelProviders.of(this)[UpdateProfileViewModel::class.java]

        etNameUpdate = findViewById(R.id.et_name_update_page)
        etEmailUpdate = findViewById(R.id.et_email_update_page)
        etAddressUpdate = findViewById(R.id.et_address_update_page)
        btnUpdate = findViewById(R.id.btn_update_profile)


        val getUserName = intent.getStringExtra("userName")
        val getUserEmail = intent.getStringExtra("userEmail")
        val getUserAddress = intent.getStringExtra("userAddress")
        val getUserId = intent.getStringExtra("userId")
        val getUserPassword = intent.getStringExtra("userPassword")


        etNameUpdate.setText(getUserName)
        etEmailUpdate.setText(getUserEmail)
        etAddressUpdate.setText(getUserAddress)

        btnUpdate.setOnClickListener {
            if(etNameUpdate.text.isEmpty() || etEmailUpdate.text.isEmpty()
                || etAddressUpdate.text.isEmpty())
            {
                Toast.makeText(this, "the field can't empty !", Toast.LENGTH_SHORT).show()
            }
            else if( updateProfileViewModel.checkName(etNameUpdate.text.toString()) != 0 ){
                Toast.makeText(this, "the name field must contains at least 5 characters", Toast.LENGTH_SHORT).show()
            }
            else if( updateProfileViewModel.checkEmail(etEmailUpdate.text.toString()) != 0 ){
                Toast.makeText(this, "the email must ends with .com or .co.id", Toast.LENGTH_SHORT).show()
            }
            else{

                // update user data firestore
                updateProfileViewModel.updateUserData( getUserId.toString(), etAddressUpdate.text.toString(), etEmailUpdate.text.toString(),
                etNameUpdate.text.toString(), getUserPassword.toString() )

                // update auth data
                updateProfileViewModel.updateAuthData(etEmailUpdate.text.toString())

                Toast.makeText(this, "updated !", Toast.LENGTH_SHORT).show()

            }
        }

    }


//    private fun updateUserData(Id : String, UserAddress : String, UserEmail : String, UserName : String, UserPassword : String){
//        val db = Firebase.firestore
//
//        Log.d("user-id-update", Id)
//
//        val data = hashMapOf(
//            "UserAddress" to UserAddress,
//            "UserEmail" to UserEmail,
//            "UserName" to UserName,
//            "UserPassword" to UserPassword
//        )
//
//
//        db.collection("users")
//            .document(Id)
//            .set(data)
//            .addOnSuccessListener {
//
//            }
//    }

}