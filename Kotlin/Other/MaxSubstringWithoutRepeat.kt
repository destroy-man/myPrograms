class MaxSubstringWithoutRepeat {
    //дана строка состоящая из любых символов, которые могут повторяться, необходимо найти длину максимальной подстроки содержащей неповторяющиеся символы
    fun getMaximumSubstringWithoutRepeat(mainStr:String):String{
        var changeMainStr=mainStr
        var maxSubstr=""
        var substr=""
        var hasSymbols=true
        while(hasSymbols) {
            for (i in changeMainStr) {
                if (!substr.contains(i))
                    substr += i
                else {
                    if(maxSubstr.length<substr.length)
                        maxSubstr = substr
                    substr = ""
                    if (changeMainStr.length > 1)
                        changeMainStr = changeMainStr.substring(1)
                    else
                        hasSymbols=false
                }
            }
        }
        return maxSubstr
    }
}