package edu.bluejack22_2.Caddo.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailViewModel:ViewModel() {

    private var db = Firebase.firestore

    fun setProductName(newProductName: String): String {
        return "Catering name: $newProductName"
    }

    fun setProductDesc(newProductDesc : String) : String{
        return "Catering description: $newProductDesc"
    }

    fun setProductCategory(newProductCategory : String):String{
        return "Product category: $newProductCategory"
    }

    fun setProductPrice(newProductPrice : String):String{
        return "Product price: $newProductPrice"
    }

    fun setProductQty(newProductQty : String):String{
        return "Product qty: $newProductQty kg"
    }

    fun setProductStatus(newProductStatus : String):String{
        return "Product status: $newProductStatus"
    }

    fun addProductToOrder(productName : String, productPrice : Int, productStatus : String, productQty : Int){

        var user = Firebase.auth.currentUser

        var getEmail : String = ""

        user?.let {
            val email = it.email
            getEmail = email.toString()
        }

        val data = hashMapOf(
            "productName" to productName,
            "productPrice" to productPrice,
            "productStatus" to productStatus,
            "productQty" to productQty,
            "productTotalPrice" to productPrice*productQty
        )

        db.collection("${getEmail}-order")
            .add(data)
            .addOnSuccessListener {documentRef->

            }
            .addOnFailureListener {

            }
    }

    fun updateProductQty(productId : String, productCategory: String, productDescription : String,
                         productName: String, productPicture : String, productPrice : Int,
                        productQty : Int, productStatus : String, sellingSuccess : Int){

        val data = hashMapOf(
            "productCategory" to productCategory,
            "productDescription" to productDescription,
            "productName" to productName,
            "productPicture" to productPicture,
            "productPrice" to productPrice,
            "productQty" to productQty-1,
            "productStatus" to productStatus,
            "sellingSuccess" to sellingSuccess+1
        )

        db.collection("products")
            .document(productId)
            .set(data)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }

    }

}