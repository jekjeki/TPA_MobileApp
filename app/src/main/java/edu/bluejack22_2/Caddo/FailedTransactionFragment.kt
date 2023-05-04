package edu.bluejack22_2.Caddo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_2.Caddo.Adapter.FailedTransactionAdapter
import edu.bluejack22_2.Caddo.Model.Transaction
import edu.bluejack22_2.Caddo.ViewModel.FailedTransactionViewModel

class FailedTransactionFragment : Fragment() {

    private lateinit var rvFailedTransaction : RecyclerView
    private lateinit var failedViewModel : FailedTransactionViewModel

    private lateinit var failedTransactionList : ArrayList<Transaction>

    private lateinit var failedTrAdapter : FailedTransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_failed_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        failedTransactionList = arrayListOf()

        rvFailedTransaction = view.findViewById(R.id.rv_failed_transaction)
        failedViewModel = ViewModelProviders.of(this).get(FailedTransactionViewModel::class.java)

        failedViewModel.getLoginData()
        failedViewModel.getCurrentLoginData()

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvFailedTransaction.setHasFixedSize(true)
        rvFailedTransaction.layoutManager = layoutManager
        failedTrAdapter = FailedTransactionAdapter(failedTransactionList)
        rvFailedTransaction.adapter = failedTrAdapter


        getFailedTransactions()

    }

    private fun getFailedTransactions(){
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("${failedViewModel.baseTrId}-transaction-failed")
            .get()
            .addOnSuccessListener { documents->
                for(document in documents){
                    failedTransactionList.add(document.toObject(Transaction::class.java))
                }

                failedTrAdapter.notifyDataSetChanged()
            }

    }
}