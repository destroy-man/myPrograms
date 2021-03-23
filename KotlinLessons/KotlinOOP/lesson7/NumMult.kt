package lesson7

class NumMult (n:Int,s:Int,q:Int):NumInc(n,s){
    override var number=n
    override var step=s
    val coefficient=q

    override fun inc(){
        number+=step*coefficient
    }

    override fun dec(){
        number-=step*coefficient
    }
}