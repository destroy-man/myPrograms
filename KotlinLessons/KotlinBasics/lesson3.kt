fun main(){
    val a=(Math.random()*100).toInt()
    when{
        a<10 || a>90->println("Bad")
        a in 51..99->println("Excellent")
        else->println("Good")
    }
}