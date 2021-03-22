package lesson5
//Пусть свойство step родительского класса NumInc имеет сеттер, проверяющий число на неравенство нулю (нулевой шаг не допустим).
open class NumInc(n: Int,gap:Int){
    open var number=n
    open var step=gap
        set(value){
            if(value!=0)
                field=value
            else
                println("Нулевой шаг не допустим!")
        }

    open fun inc(){
        number+=step
    }

    open fun dec(){
        number-=step
    }

    override fun toString(): String {
        val n="number = $number"
        val s="step = $step"
        return "$n \n $s"
    }
}