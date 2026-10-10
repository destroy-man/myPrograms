package ru.korobeynikov.p06composetesting

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class ComposeTest {

    //Создание ComposeRule с привязкой к Activity
    //@get:Rule
    //val composeTestRule = createAndroidComposeRule<MainActivity>()

    //Создание ComposeRule с привязкой к Composable функции
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun buttonClickTest() {
        composeTestRule.setContent {
            Counter()
        }
        composeTestRule.onNodeWithTag(CounterTags.BUTTON).performClick()
        composeTestRule.onNodeWithTag(CounterTags.TEXT).assertTextEquals("1")
        composeTestRule.onNodeWithTag(CounterTags.BUTTON).performClick()
        composeTestRule.onNodeWithTag(CounterTags.TEXT).assertTextEquals("2")
        //1 вариант проверить текст кнопки
        composeTestRule.onNodeWithTag(CounterTags.BUTTON_TEXT, useUnmergedTree = true)
            .assertTextEquals("Add")
        //2 вариант проверить текст кнопки
        composeTestRule.onNodeWithTag(CounterTags.BUTTON).assertTextEquals("Add")
    }

    @Test
    fun listLazyColumnTest() {
        composeTestRule.setContent {
            ListScreen()
        }
        //Скроллим до 25 элемента в списке
        composeTestRule.onNodeWithTag(ListTags.LIST)
            .performScrollToNode(hasAnyDescendant(hasText("item 25")))
        //Находим 25 элемент с иконкой и проверяем что он отображается
        composeTestRule.onAllNodesWithTag(ListTags.ITEM)
            .filterToOne(
                hasAnyDescendant(hasText("item 25"))
                        and hasAnyDescendant(hasTestTag(ListTags.ICON))
            ).assertIsDisplayed()
        //Находим 26 элемент без иконки и проверяем что он отображается
        composeTestRule.onAllNodesWithTag(ListTags.ITEM)
            .filterToOne(
                hasAnyDescendant(hasText("item 26"))
                        and !hasAnyDescendant(hasTestTag(ListTags.ICON))
            ).assertIsDisplayed()
        //Находим иконку 25 элемента и нажимаем на нее
        composeTestRule.onAllNodesWithTag(ListTags.ITEM)
            .filterToOne(
                hasAnyDescendant(hasText("item 25"))
                        and hasAnyDescendant(hasTestTag(ListTags.ICON))
            )
            .onChildren()
            .filterToOne(hasTestTag(ListTags.ICON))
            .performClick()
        //Находим 25 элемент с иконкой и проверяем что он не отображается
        composeTestRule.onAllNodesWithTag(ListTags.ITEM)
            .filterToOne(
                hasAnyDescendant(hasText("item 25"))
                        and hasAnyDescendant(hasTestTag(ListTags.ICON))
            ).assertDoesNotExist()
    }

    @Test
    fun editTextTest() {
        composeTestRule.setContent {
            EditTextScreen()
        }
        composeTestRule.onNodeWithTag(EditTextTags.EDIT_TEXT).performTextInput("abcdefg")
        composeTestRule.onNodeWithTag(EditTextTags.EDIT_TEXT).assertTextEquals("abcdefg")
        composeTestRule.onNodeWithTag(EditTextTags.EDIT_TEXT).performTextClearance()
        composeTestRule.onNodeWithTag(EditTextTags.EDIT_TEXT).assertTextEquals("")
    }
}