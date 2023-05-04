package edu.bluejack22_2.Caddo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import edu.bluejack22_2.Caddo.Adapter.ViewPagerAdapter

class TransactionFragment : Fragment() {

    private lateinit var viewPager : ViewPager
    private lateinit var tabLayout : TabLayout
    private lateinit var titleTransactionPage : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTransactionPage = view.findViewById(R.id.title_transaction_page)
        viewPager = view.findViewById(R.id.view_pager_transaction)
        tabLayout = view.findViewById(R.id.tabLayout)


        titleTransactionPage.text = getString(R.string.title_transaction_page)
        viewPager.adapter = ViewPagerAdapter(childFragmentManager, 2)
        tabLayout.setupWithViewPager(viewPager)
    }

}