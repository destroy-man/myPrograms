package lesson2

class NumInc{

    var number=0
    var step=1

    constructor(){}

    constructor(num:Int){
        number=num
    }

    constructor(num:Int,gap:Int){
        number=num
        step=gap
    }

    fun inc(){
        number+=step
    }

    fun dec(){
        number-=step
    }
}