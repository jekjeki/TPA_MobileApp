package edu.bluejack22_2.Caddo.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import edu.bluejack22_2.Caddo.FailedTransactionFragment
import edu.bluejack22_2.Caddo.SuccessTransactionFragment

class ViewPagerAdapter(fm : FragmentManager, var tabCount : Int) : FragmentPagerAdapter(fm){

    private val fragmentTitleList = mutableListOf("Success", "Failed")

    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0->return SuccessTransactionFragment()
            else -> return FailedTransactionFragment()
        }
    }

}