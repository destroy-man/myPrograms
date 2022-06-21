package ru.korobeynikov.p1151multiplescreen

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity:FragmentActivity(),TitlesFragment.onItemClickListener{

    var position=0
    var withDetails=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState!=null)
            position=savedInstanceState.getInt("position")
        withDetails=cont!=null
        if(withDetails)
            showDetails(position)
    }

    fun showDetails(pos:Int){
        if(withDetails){
            var details=supportFragmentManager.findFragmentById(R.id.cont) as DetailsFragment?
            if(details==null || details.getPosition()!=pos){
                details=DetailsFragment.newInstance(pos)
                supportFragmentManager.beginTransaction().replace(R.id.cont,details).commit()
            }
        }
        else
            startActivity(Intent(this,DetailsActivity::class.java).putExtra("position",position))
    }

    override fun itemClick(position: Int) {
        this.position=position
        showDetails(position)
    }

    protected override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("position",position)
    }
}