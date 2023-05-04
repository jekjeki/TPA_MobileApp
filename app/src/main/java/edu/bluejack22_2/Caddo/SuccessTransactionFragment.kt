package edu.bluejack22_2.Caddo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import edu.bluejack22_2.Caddo.Adapter.SuccessTransactionAdapter
import edu.bluejack22_2.Caddo.Model.Transaction
import edu.bluejack22_2.Caddo.ViewModel.SuccessTransactionViewModel

class SuccessTransactionFragment : Fragment() {

    private lateinit var rvSuccessTransaction : RecyclerView
    private lateinit var successAdapter : SuccessTransactionAdapter
    private lateinit var successViewModel : SuccessTransactionViewModel

    private lateinit var successTransactionList : ArrayList<Transaction>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        successTransactionList = arrayListOf()

        rvSuccessTransaction = view.findViewById(R.id.rv_successTransaction)

        successViewModel = ViewModelProviders.of(this)[SuccessTransactionViewModel::class.java]

        successViewModel.getCurrentUser()
        successViewModel.getBaseTransactionId()

        getSuccessTransaction()


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvSuccessTransaction.setHasFixedSize(true)
        rvSuccessTransaction.layoutManager = layoutManager
        successAdapter = SuccessTransactionAdapter(successTransactionList)
        rvSuccessTransaction.adapter = successAdapter



    }

    private fun getSuccessTransaction(){
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        Log.d("base-tr-id", "${successViewModel.baseTrId}")
        db.collection("${successViewModel.baseTrId}-transaction-success")
            .get()
            .addOnSuccessListener {documents->
                for(document in documents){
                    successTransactionList.add(document.toObject(Transaction::class.java))
                    Log.d("document-success-id", "${document.id}")
                }
                successAdapter.notifyDataSetChanged()
                Log.d("list-success-trans", "$successTransactionList")
            }
    }

}