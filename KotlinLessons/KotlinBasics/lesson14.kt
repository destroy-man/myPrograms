fun main(){
    val stars=edges("***")
    val block=edges("|")
    println(stars("Hello"))
    println(stars("World"))
    println(block("Earth"))
}

fun edges(str:String):(String)->String{
    return {"$str$it$str"}
}