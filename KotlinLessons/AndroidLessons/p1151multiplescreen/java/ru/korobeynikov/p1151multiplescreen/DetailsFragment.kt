package ru.korobeynikov.p1151multiplescreen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class DetailsFragment:Fragment(){

    companion object{
        fun newInstance(pos:Int):DetailsFragment{
            val details=DetailsFragment()
            val args=Bundle()
            args.putInt("position",pos)
            details.arguments=args
            return details
        }
    }

    fun getPosition():Int{
        return if(arguments!=null)
            arguments!!.getInt("position",0)
        else 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.details,container,false)
        val tv=v.findViewById<TextView>(R.id.tvText)
        tv.text=resources.getStringArray(R.array.content)[getPosition()]
        return v
    }
}