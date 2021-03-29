fun main(){
    val a:Something<String,Int>
    a=Something("Hello",5)
}

class Something<T,V>(p:T,q:V){
    val prop:T=p
    val qty:V=q
}