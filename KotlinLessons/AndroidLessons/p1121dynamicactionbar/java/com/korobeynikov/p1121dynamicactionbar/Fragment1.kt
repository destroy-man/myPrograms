package com.korobeynikov.p1121dynamicactionbar

import android.app.Fragment
import android.os.Bundle
import android.view.*

class Fragment1:Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment1,null)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fragment1,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}