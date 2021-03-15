fun main(){
    //Усовершенствуйте программу из предыдущего урока, позволив пользователю выполнять арифметические вычисления до тех пор, пока вместо первого числа он не введет слово "stop".
    var i1= readLine()!!
    var i2= readLine()!!
    var action= readLine()
    while(action!="stop") {
        when (action) {
            "+" -> println(i1.toInt() + i2.toInt())
            "-" -> println(i1.toInt() - i2.toInt())
            "*" -> println(i1.toInt() * i2.toInt())
            "/" -> println(i1.toInt() / i2.toInt())
        }
        i1= readLine()!!
        i2= readLine()!!
        action= readLine()
    }
}