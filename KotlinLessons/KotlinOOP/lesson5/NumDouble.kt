package lesson5

class NumDouble(n:Int,gap:Int): NumInc(n,gap){
    override var number=n
    override var step=gap

    override fun inc(){
        super.inc()
        number+=step
    }

    override fun dec(){
        super.dec()
        number-=step
    }
}