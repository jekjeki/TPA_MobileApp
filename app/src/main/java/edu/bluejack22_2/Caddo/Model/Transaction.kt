package edu.bluejack22_2.Caddo.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    var productName : String? = null,
    var totalPrice : Int? = null,
    var transactionStatus : String? = null
) : Parcelable