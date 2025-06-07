package ru.korobeynikov.app1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Headings() {
    Column {
        Heading("Если вы не можете удалить его, просто переименуйте его!")
        Text(
            text = "На конференции I/O мне посчастливилось посетить выступление «Android Studio: " +
                    "советы и рекомендации», где Иван Гравилович из Google поделился несколькими " +
                    "замечательными советами. Одним из них было возможное решение моей проблемы: " +
                    "настройка собственного пути для моих модулей."
        )
        Heading("Заключение")
        Text(
            text = "Как следует из названия, это действительно мелочь, но она помогает " +
                    "поддерживать порядок в моем проекте и показывает, как небольшая " +
                    "конфигурация Gradle может помочь поддерживать порядок в вашем проекте."
        )
        Heading("Ресурсы")
        Text("Android Studio: советы и рекомендации (Google I/O’19)")
        Text("Модуль On Demand codelab")
        Text("Лоскутный плед — история модульности")
    }
}

@Composable
private fun Heading(text: String) {
    Text(
        modifier = Modifier
            .padding(4.dp)
            .semantics { heading() },
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
}