package ru.korobeynikov.p0541customadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class BoxAdapter:BaseAdapter {

    lateinit var ctx:Context
    lateinit var lInflater:LayoutInflater
    lateinit var objects:ArrayList<Product>

    constructor(context:Context,products:ArrayList<Product>){
        ctx=context
        objects=products
        lInflater=ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(position: Int): Any {
        return objects.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view=convertView
        if(view==null)
            view=lInflater.inflate(R.layout.item,parent,false)
        val p=getProduct(position)
        view?.findViewById<TextView>(R.id.tvDescr)?.text=p.name
        view?.findViewById<TextView>(R.id.tvPrice)?.text=p.price.toString()+""
        view?.findViewById<ImageView>(R.id.ivImage)?.setImageResource(p.image)
        val cbBuy=view?.findViewById<CheckBox>(R.id.cbBox)
        cbBuy?.setOnCheckedChangeListener(myCheckChangeList)
        cbBuy?.tag=position
        cbBuy?.isChecked=p.box
        return view
    }

    fun getProduct(position:Int):Product{
        return getItem(position) as Product
    }

    fun getBox():ArrayList<Product>{
        val box=ArrayList<Product>()
        for(p in objects)
            if(p.box)
                box.add(p)
        return box
    }

    val myCheckChangeList=CompoundButton.OnCheckedChangeListener{buttonView,isChecked ->
        getProduct(buttonView.tag as Int).box=isChecked
    }
}