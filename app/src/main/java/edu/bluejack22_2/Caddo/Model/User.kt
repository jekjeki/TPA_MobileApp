package edu.bluejack22_2.Caddo.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val UserName : String? = null,
    val UserEmail : String? = null,
    val UserAddress : String? = null,
    val UserPassword : String? = null
) : Parcelable