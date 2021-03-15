import java.lang.ArithmeticException
import java.lang.NumberFormatException

fun main(){
    //Доработайте программу из данного урока так, чтобы она не завершалась до тех пор, пока пользователь не введет два числа, и второе из них не будет нулем.
    var c: Int
    var isException=true
    while(isException) {
        isException=false
        var a=readLine()!!
        var b=readLine()!!
        try {
            c = a.toInt() / b.toInt()
            println(c)
        }
        catch(e:NumberFormatException){
            println("You only need to enter numbers")
            isException=true
        }
        catch(e:ArithmeticException){
            println("You cannot divide by zero")
            isException=true
        }
    }
    println("Program finish!")
}