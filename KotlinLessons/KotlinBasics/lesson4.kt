fun main(){
    val a:Array<IntArray> = Array(2){IntArray(4){0}}
    a[0]=intArrayOf(1,2,3,4)
    a[1]=intArrayOf(5,6,7,8)
    for(i in a){
        for(j in i){
            print(" $j")
        }
        println()
    }
}