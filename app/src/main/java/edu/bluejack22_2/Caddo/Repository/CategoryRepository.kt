package edu.bluejack22_2.Caddo.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import edu.bluejack22_2.Caddo.Model.Category

class CategoryRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("categories")

    @Volatile private var INSTANCE : CategoryRepository ?= null

    // get instance
    fun getInstance() : CategoryRepository{
        return INSTANCE ?: synchronized(this){
            val instance = CategoryRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadCategories(categoryList : MutableLiveData<List<Category>>){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {

                    val _categoryList : List<Category> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Category::class.java)!!
                    }

                    categoryList.postValue(_categoryList)

                } catch(e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}