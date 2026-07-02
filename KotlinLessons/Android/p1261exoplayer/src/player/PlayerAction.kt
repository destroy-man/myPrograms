package ru.korobeynikov.p1261exoplayer.player

data class PlayerAction(
    val actionType: ActionType,
    val data: Any? = null
)
