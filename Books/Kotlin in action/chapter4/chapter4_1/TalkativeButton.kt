package ru.korobeynikov.chapter4.chapter4_1

internal open class TalkativeButton : Focusable {

    private fun yell() = "Hey!"

    protected fun whisper() = "Let's talk!"
}

//fun TalkativeButton.giveSpeech(){
//    yell()
//    whisper()
//}