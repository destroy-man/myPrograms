package com.korobeynikov.p0541customadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val products=ArrayList<Product>()
    lateinit var boxAdapter:BoxAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillData()
        boxAdapter=BoxAdapter(this,products)
        lvMain.adapter=boxAdapter
    }

    fun fillData(){
        for(i in 1..20)
            products.add(Product("Product "+i,i*1000,R.drawable.ic_launcher_foreground,false))
    }

    fun showResult(v:View){
        var result="Товары в корзине:"
        for(p in boxAdapter.getBox())
            if(p.box)
                result+="\n"+p.name
        Toast.makeText(this,result,Toast.LENGTH_LONG).show()
    }
}