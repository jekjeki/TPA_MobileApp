package edu.bluejack22_2.Caddo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_2.Caddo.Model.User
import edu.bluejack22_2.Caddo.Model.UserReadModel
import edu.bluejack22_2.Caddo.ViewModel.ProfileViewModel


class ProfileFragment : Fragment() {

    private lateinit var ivProfile : ImageView
    private lateinit var tvProfileName : TextView
    private lateinit var tvProfileEmail : TextView
    private lateinit var tvProfileAddress : TextView
    private lateinit var btnUpdateProfile : Button

    private lateinit var tvTitleFullName : TextView
    private lateinit var tvTitleEmail : TextView
    private lateinit var tvTitleAddress : TextView

    private lateinit var profileViewModel : ProfileViewModel

    private var documentId = ""
    private var userPassword = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelProviders.of(this)[ProfileViewModel::class.java]

        getUsers()

        tvTitleFullName = view.findViewById(R.id.title_full_name)
        tvTitleEmail = view.findViewById(R.id.title_email)
        tvTitleAddress = view.findViewById(R.id.title_profile_address)

        ivProfile = view.findViewById(R.id.iv_profile_page)
        tvProfileName = view.findViewById(R.id.tv_profile_name)
        tvProfileEmail = view.findViewById(R.id.tv_profile_email)
        tvProfileAddress = view.findViewById(R.id.tv_profile_address)
        btnUpdateProfile = view.findViewById(R.id.btn_update_profile)

        // tv connect with string
        tvTitleFullName.text = getString(R.string.title_profile_fullName)
        tvTitleEmail.text = getString(R.string.title_profile_email)
        tvTitleAddress.text = getString(R.string.title_profile_address)

        profileViewModel.getCurrentUserProfileData()

        Glide.with(this).load(R.drawable.baseline_person_24).into(ivProfile)

        btnUpdateProfile.setOnClickListener {
            val intent = Intent(context, UpdateUserPage::class.java)
            intent.putExtra("userId", documentId)
            intent.putExtra("userName", tvProfileName.text.toString())
            intent.putExtra("userEmail", tvProfileEmail.text.toString())
            intent.putExtra("userPassword", userPassword)
            intent.putExtra("userAddress", tvProfileAddress.text.toString())
            startActivity(intent)
        }

    }

    private fun getUsers(){

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("users")
            .whereEqualTo("UserEmail", profileViewModel.getEmail)
            .get()
            .addOnSuccessListener {documents->
                for(document in documents){
                    Log.d("list-users", "${document.data}")

                    documentId = "${document.id}"
                    userPassword = "${document.data["UserPassword"].toString()}"
                    tvProfileName.text = document.data["UserName"].toString()
                    tvProfileEmail.text = document.data["UserEmail"].toString()
                    tvProfileAddress.text = document.data["UserAddress"].toString()
                    Log.d("documentId", "$documentId")
                    Log.d("user-password", "$userPassword")
                }
            }
            .addOnFailureListener {

            }

    }


}