package lesson7

class NumDouble(n:Int,s:Int):NumInc(n,s) {
    override var number=n
    override var step=s
    override fun inc(){
        number+=2*step
    }

    override fun dec(){
        number-=2*step
    }
}