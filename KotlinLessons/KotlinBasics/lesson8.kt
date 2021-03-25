fun main(){
    val a:IntArray=intArrayOf(0,1,8)
    println(avr(*a))
}

fun avr(vararg nums:Int):Float{
    val sum=nums.sum()
    val l=nums.size
    return sum.toFloat()/l
}