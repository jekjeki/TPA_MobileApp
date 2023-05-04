package edu.bluejack22_2.Caddo.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var productCategory : String? = null,
    var productDescription : String? = null,
    var productName : String? = null,
    var productPicture : String? = null,
    var productPrice : Int? = null,
    var productQty : Int? = null,
    var productStatus : String? = null,
    var sellingSuccess : Int? = null
) : Parcelable