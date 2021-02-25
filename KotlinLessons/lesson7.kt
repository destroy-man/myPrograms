fun main(){
    //Напишите программу, которая запрашивает с ввода два числа, знак операции (+, -, * или /) и выполняет над числами указанную операцию.
    val i1= readLine()!!
    val i2= readLine()!!
    val action= readLine()
    when(action){
        "+"->println(i1.toInt()+i2.toInt())
        "-"->println(i1.toInt()-i2.toInt())
        "*"->println(i1.toInt()*i2.toInt())
        "/"->println(i1.toInt()/i2.toInt())
    }
}