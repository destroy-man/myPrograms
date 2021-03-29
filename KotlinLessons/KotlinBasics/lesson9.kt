fun main(){
    val obj1=MyClass(12,3)
    obj1.member()
    obj1.extension()
}

class MyClass(val a:Int=0,val b:Int=0){
    fun member(){
        println("member-function")
        println(a+b)
    }
}

fun MyClass.extension(){
    println("extension-function")
    println(a-b)
}