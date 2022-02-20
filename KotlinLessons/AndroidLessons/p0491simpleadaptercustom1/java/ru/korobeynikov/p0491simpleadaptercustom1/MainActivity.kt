package  ru.korobeynikov.p0491simpleadaptercustom1

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val ATTRIBUTE_NAME_TEXT="text"
    val ATTRIBUTE_NAME_VALUE="value"
    val ATTRIBUTE_NAME_IMAGE="image"
    val positive=android.R.drawable.arrow_up_float
    val negative=android.R.drawable.arrow_down_float

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val values=arrayOf(8,4,-3,2,-5,0,3,-6,1,-1)
        val data=ArrayList<Map<String,Any>>(values.size)
        var m:Map<String,Any>
        var img=0
        for(i in values.indices){
            m=HashMap<String,Any>()
            m[ATTRIBUTE_NAME_TEXT]="Day "+(i+1)
            m[ATTRIBUTE_NAME_VALUE]=values[i]
            if(values[i]==0)img=0
            else img=if(values[i]>0)positive else negative
            m[ATTRIBUTE_NAME_IMAGE]=img
            data.add(m)
        }
        val from=arrayOf(ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_VALUE,ATTRIBUTE_NAME_IMAGE)
        val to=arrayOf(R.id.tvText,R.id.tvValue,R.id.ivImg)
        val sAdapter=MySimpleAdapter(this,data,R.layout.item,from,to)
        lvSimple.adapter=sAdapter
    }

    inner class MySimpleAdapter : SimpleAdapter {

        constructor(context: Context,data: List<Map<String,*>?>?,resource:Int,from: Array<String>,to: Array<Int>):
                super(context,data,resource,from,to.toIntArray())

        override fun setViewText(v: TextView?, text: String?) {
            super.setViewText(v, text)
            if(v?.id==R.id.tvValue){
                val i=text?.toInt()
                if(i!=null){
                    if(i<0)v.setTextColor(Color.RED)
                    else if(i>0)v.setTextColor(Color.GREEN)
                }
            }
        }

        override fun setViewImage(v: ImageView?, value: Int) {
            super.setViewImage(v, value)
            if(value==negative)v?.setBackgroundColor(Color.RED)
            else if(value==positive)v?.setBackgroundColor(Color.GREEN)
        }
    }
}