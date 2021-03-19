package lesson4

class NumMult:NumInc{
    var coefficient=2

    constructor(num:Int,gap:Int,coef:Int):super(num,gap){
        coefficient=coef
    }

    constructor(coef: Int):this(0,1,2){
        coefficient=coef
    }

    fun mult(){
        number*=coefficient
    }
}