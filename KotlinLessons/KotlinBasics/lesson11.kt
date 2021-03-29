import Fruits.*

fun main(){
    println(ORANGE.qty)
}

enum class Fruits(var qty:Int,var price:Double){
    BANANA(10, 0.9),APPLE(12,1.1),ORANGE(5,1.5),KIWI(15,1.8);

    fun sell(n:Int){
        qty-=n
    }

    fun changePrice(newPrice:Double){
        price=newPrice
    }
}