package lesson6

open class NumInc(var number:Int,var step:Int) {
    //Операциям инкремента и декремента в Kotlin соответствуют функции с именами inc() и dec(). Измените функции-члены inc() и dec() нашего класса так, чтобы они перестали быть "обычными", а перегружали соответствующие им операторы. При этом инкремент экземпляра класса NumInc должен также выполнять увеличение number на step, декремент – уменьшение на step.
    operator fun inc():NumInc{
        number+=step
        return NumInc(number,step)
    }

    operator fun dec():NumInc{
        number-=step
        return NumInc(number,step)
    }

    operator fun get(i:Int):Int?{
        when(i){
            0->return number
            1->return step
            else->return null
        }
    }

    operator fun set(i:Int,v:Int){
        when(i){
            0->number=v
            1->step=v
        }
    }
}