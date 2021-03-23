package lesson8

class NumInc(n:Int,s:Int):ArbitraryStep{
    var number=n
    var step=s
    override var arbitraryStep=0

    fun inc(){
        number+=step
    }

    fun dec(){
        number-=step
    }

    override fun incAS(gap: Int) {
        arbitraryStep=gap
        number+=gap
    }
}