package ru.korobeynikov.databasegames

import android.widget.TextView
import androidx.databinding.BindingAdapter

//Класс для преобразования списка игр в текст
class ListGamesBindings {
    companion object {
        @JvmStatic
        @BindingAdapter("games")
        fun setListGames(view: TextView, games: List<Game>) {
            val textGames = StringBuilder()
            for (i in games.indices) {
                val game = games[i]
                textGames.append(" ${i + 1}. ${game.name} (${game.year}) = ${game.rating}\n")
            }
            if (textGames.isNotEmpty())
                view.text = textGames.toString()
            else
                view.text = ""
        }
    }
}