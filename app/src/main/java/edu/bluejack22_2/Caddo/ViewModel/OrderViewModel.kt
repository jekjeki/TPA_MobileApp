package edu.bluejack22_2.Caddo.ViewModel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class OrderViewModel : ViewModel() {

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()


    fun setProductName(productName: String): String {
        return "Product name: $productName"
    }

    fun setOrderQty(orderQty : Int):String{
        return "Order quantity: $orderQty"
    }

    fun setTotalPrice(orderTotalPrice : Int):String{
        return "Total price: $orderTotalPrice"
    }

    var userBaseTrId = ""

    // get user base transaction data
    fun getUserBaseTransactions(){

        var getEmail = ""


        var user = Firebase.auth.currentUser

        user?.let {
            getEmail = it.email.toString()
        }

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("users")
            .whereEqualTo("UserEmail", getEmail)
            .get()
            .addOnSuccessListener { documents->
                for(document in documents){
                    userBaseTrId = "${document.data["UserBaseTransactionId"].toString()}"
                }
                Log.d("userBaseTrId-order", userBaseTrId)
            }

    }


    fun addTransaction(productName : String, totalProductPrice : Int, transactionStatus : String) {

        var user = Firebase.auth.currentUser

        var getEmail: String = ""


        // users base data
        var UserDocumentId: String = ""
        var UserAddress: String = ""
        var UserEmail: String = ""
        var UserName: String = ""
        var UserPassword: String = ""

        user?.let {
            getEmail = it.email.toString()
        }

        // data transaction success and failed
        val data = hashMapOf(
            "productName" to productName,
            "totalPrice" to totalProductPrice,
            "transactionStatus" to transactionStatus
        )
        if (transactionStatus.equals("success")) {
            db.collection("${userBaseTrId}-transaction-success")
                .add(data)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
        } else {

            db.collection("${userBaseTrId}-transaction-failed")
                .add(data)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
        }


        // get data current user login
        db.collection("users")
            .whereEqualTo("UserEmail", getEmail)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    UserDocumentId = "${document.id}"
                    UserAddress = "${document.data["UserAddress"].toString()}"
                    UserEmail = "${document.data["UserEmail"].toString()}"
                    UserName = "${document.data["UserName"].toString()}"
                    UserPassword = "${document.data["UserPassword"].toString()}"
                }
            }
            .addOnFailureListener {

            }

    }



}