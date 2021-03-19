package lesson1

class NumInc {
    var number=0
    var step=1

    fun inc(){
        number+=step
    }

    fun dec(){
        number-=step
    }
    //Добавьте в класс еще одну функцию, которая увеличивает число не на шаг-свойство, а на число, которое передается в функцию в качестве аргумента.
    fun incArg(stepAdd:Int){
        number+=stepAdd
    }
}