package lesson4
//Переделайте последний пример классов из урока так, чтобы родительский класс содержал только один конструктор, а дочерний – два.
open class NumInc(n:Int,gap:Int){
    var number=n
    var step=gap

    fun inc(){
        number+=step
    }

    fun dec(){
        number-=step
    }
}