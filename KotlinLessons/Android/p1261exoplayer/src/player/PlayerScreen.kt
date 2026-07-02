package ru.korobeynikov.p1261exoplayer.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.PlayerSurface
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun PlayerRoute(modifier: Modifier = Modifier, playerViewModel: PlayerViewModel = viewModel()) {
    val exoPlayer = playerViewModel.playerState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Box(modifier.fillMaxSize()) {
        exoPlayer.value?.let { exoPlayerValue ->
            PlayerScreen(exoPlayer = exoPlayerValue, playerActions = playerViewModel::executeAction)
        }
    }

    LaunchedEffect(Unit) {
        playerViewModel.createPlayerWithMediaItems(context)
    }

    LaunchedEffect(Unit) {
        while (true) {
            exoPlayer.value?.currentMediaItem?.let { mediaItem ->
                playerViewModel.updateCurrentPosition(
                    mediaItem.mediaId,
                    exoPlayer.value?.currentPosition ?: 0
                )
            }
            delay(1.seconds)
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> playerViewModel.executeAction(PlayerAction(ActionType.PAUSE))
                Lifecycle.Event.ON_RESUME -> playerViewModel.executeAction(PlayerAction(ActionType.PLAY))
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    exoPlayer: ExoPlayer,
    playerActions: (PlayerAction) -> Unit
) {
    Box(modifier = modifier) {
        PlayerSurface(
            player = exoPlayer,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9)
                .align(Alignment.Center)
        )
        VideoControls(exoPlayer, playerActions)
    }
}

@Composable
fun VideoControls(player: ExoPlayer, playerActions: (PlayerAction) -> Unit) {
    var isPlaying by remember { mutableStateOf(player.isPlaying) }
    var controlsVisible by remember { mutableStateOf(true) }

    if (controlsVisible) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.4f))
            .clickable {
                controlsVisible = !controlsVisible
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    playerActions(PlayerAction(ActionType.PREVIOUS))
                }) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                IconButton(onClick = {
                    playerActions(PlayerAction(ActionType.REWIND))
                }) {
                    Icon(
                        imageVector = Icons.Default.Replay10,
                        contentDescription = "Rewind 10 seconds",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                IconButton(onClick = {
                    playerActions(
                        PlayerAction(
                            if (player.isPlaying) ActionType.PAUSE
                            else ActionType.PLAY
                        )
                    )
                }) {
                    Icon(
                        imageVector = if (player.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }

                IconButton(onClick = {
                    playerActions(PlayerAction(ActionType.FORWARD))
                }) {
                    Icon(
                        imageVector = Icons.Default.Forward10,
                        contentDescription = "Forward 10 seconds",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                IconButton(onClick = {
                    playerActions(PlayerAction(ActionType.NEXT))
                }) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    } else {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                controlsVisible = true
            }
        )
    }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying = isPlayingNow
            }
        }
        player.addListener(listener)

        onDispose {
            player.removeListener(listener)
        }
    }

    LaunchedEffect(isPlaying, controlsVisible) {
        if (isPlaying && controlsVisible) {
            delay(3.seconds)
            controlsVisible = false
        }
    }
}