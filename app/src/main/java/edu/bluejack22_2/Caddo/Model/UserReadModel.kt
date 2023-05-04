package edu.bluejack22_2.Caddo.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserReadModel(
    val UserAddress : String? = null,
    val UserEmail : String? = null,
    val UserName : String? = null,
    val UserPassword : String? = null
) : Parcelable