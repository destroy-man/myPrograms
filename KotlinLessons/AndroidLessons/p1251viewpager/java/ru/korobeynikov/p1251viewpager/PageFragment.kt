package ru.korobeynikov.p1251viewpager

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

class PageFragment:Fragment() {

    companion object{

        val TAG="myLogs"
        val ARGUMENT_PAGE_NUMBER="arg_page_number"
        val SAVE_PAGE_NUMBER="save_page_number"

        fun newInstance(page:Int):PageFragment{
            val pageFragment=PageFragment()
            val arguments=Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER,page)
            pageFragment.arguments=arguments
            return pageFragment
        }
    }

    var pageNumber=0
    var backColor=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null)
            pageNumber=arguments!!.getInt(ARGUMENT_PAGE_NUMBER)
        Log.d(TAG,"onCreate: $pageNumber")
        val rnd=Random()
        backColor=Color.argb(40,rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))
        var savedPageNumber=-1
        if(savedInstanceState!=null)
            savedPageNumber=savedInstanceState.getInt(SAVE_PAGE_NUMBER)
        Log.d(TAG,"savedPageNumber = $savedPageNumber")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment,null)
        val tvPage=view.findViewById<TextView>(R.id.tvPage)
        tvPage.text="Page $pageNumber"
        tvPage.setBackgroundColor(backColor)
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVE_PAGE_NUMBER,pageNumber)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy: $pageNumber")
    }
}