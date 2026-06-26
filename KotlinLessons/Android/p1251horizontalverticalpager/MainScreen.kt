package ru.korobeynikov.p1251horizontalverticalpager

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun MainScreen() {
    //Pager with auto advance pages
    val pageItemColors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount = { pageItemColors.size })
        val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

        val pageInteractionSource = remember { MutableInteractionSource() }
        val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

        val autoAdvance = !pagerIsDragged && !pageIsPressed

        if (autoAdvance) {
            LaunchedEffect(pagerState, pageInteractionSource) {
                while (true) {
                    delay(2.seconds)
                    val nextPage = (pagerState.currentPage + 1) % pageItemColors.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        HorizontalPager(state = pagerState) { page ->
            Text(
                text = "Page: $page",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(pageItemColors[page])
                    .wrapContentSize(align = Alignment.Center)
            )
        }
        PagerIndicator(pageItemColors.size, pagerState.currentPage)
    }
}

@Composable
fun PagerIndicator(pageCount: Int, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color = if (currentPageIndex == iteration) Color.DarkGray else Color.LightGray
                Box(modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp)
                )
            }
        }
    }
}

@Composable
fun PagerWithIndicatorPagesScreen() {
    Column {
        val pagerState = rememberPagerState(pageCount = { 4 })
        HorizontalPager(state = pagerState) { page ->
            Text(text = "Page: ${page + 1}", modifier = Modifier.fillMaxWidth())
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }
}

@Composable
fun ObserveCurrentPageScreen() {
    Column {
        val pagerState = rememberPagerState(pageCount = { 10 })
        LaunchedEffect(pagerState) {
            snapshotFlow {
                pagerState.currentPage
            }.collect { page ->
                Log.d("myLogs", "Page changed to ${page + 1}")
            }
        }
        HorizontalPager(state = pagerState) { page ->
            Text(text = "Page: ${page + 1}", modifier = Modifier
                .fillMaxWidth()
                .height(100.dp))
        }
    }
}

@Composable
fun AnimateScrollToPageScreen() {
    Column {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = { 10 })
        HorizontalPager(state = pagerState) { page ->
            Text(text = "Page: ${page + 1}", modifier = Modifier
                .fillMaxWidth()
                .height(100.dp))
        }
        Button(modifier = Modifier.align(alignment = Alignment.CenterHorizontally), onClick = {
            scope.launch {
                pagerState.animateScrollToPage(4)
            }
        }) {
            Text("Jump to Page 5")
        }
    }
}

@Composable
fun DefaultScrollToPageScreen() {
    Column {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = { 10 })
        HorizontalPager(state = pagerState) { page ->
            Text(text = "Page: ${page + 1}", modifier = Modifier
                .fillMaxWidth()
                .height(100.dp))
        }
        Button(modifier = Modifier.align(alignment = Alignment.CenterHorizontally), onClick = {
            scope.launch {
                pagerState.scrollToPage(4)
            }
        }) {
            Text("Jump to Page 5")
        }
    }
}

@Composable
fun VerticalPagerScreen() {
    val pagerState = rememberPagerState(pageCount = { 10 })
    VerticalPager(state = pagerState) { page ->
        Text(text = "Page: ${page + 1}", modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun HorizontalPagerScreen() {
    val pagerState = rememberPagerState(pageCount = { 10 })
    HorizontalPager(state = pagerState) { page ->
        Text(text = "Page: ${page + 1}", modifier = Modifier.fillMaxWidth())
    }
}