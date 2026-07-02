package ru.korobeynikov.p1261exoplayer.player

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.COMMAND_SEEK_TO_MEDIA_ITEM
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class PlayerViewModel : ViewModel() {

    companion object {
        const val Video_1 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        const val Video_2 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
        const val Video_3 =
            "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8"
        const val Video_4 = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
    }

    private val _playerState = MutableStateFlow<ExoPlayer?>(null)
    val playerState: StateFlow<ExoPlayer?> = _playerState

    private val hashMapVideoStates = mutableMapOf<String, VideoItem>()

    fun createPlayerWithMediaItems(context: Context) {
        if (_playerState.value == null) {
            val mediaItems = listOf(
                MediaItem.Builder().setUri(Video_1).setMediaId("Video_1").build(),
                MediaItem.Builder().setUri(Video_2).setMediaId("Video_2").build(),
                MediaItem.Builder().setUri(Video_3).setMediaId("Video_3").build(),
                MediaItem.Builder().setUri(Video_4).setMediaId("Video_4").build()
            )
            _playerState.update {
                ExoPlayer.Builder(context).build().apply {
                    setMediaItems(mediaItems)
                    prepare()
                    playWhenReady = true
                }
            }
        }
    }

    fun updateCurrentPosition(id: String, position: Long) {
        hashMapVideoStates[id] = hashMapVideoStates[id]?.copy(currentPosition = position)
            ?: VideoItem(currentPosition = position)
    }

    fun executeAction(playerAction: PlayerAction) {
        when (playerAction.actionType) {
            ActionType.PLAY -> _playerState.value?.play()
            ActionType.PAUSE -> _playerState.value?.pause()
            ActionType.REWIND -> _playerState.value?.rewind()
            ActionType.FORWARD -> _playerState.value?.forward()
            ActionType.NEXT -> _playerState.value?.playNext()
            ActionType.PREVIOUS -> _playerState.value?.playPrevious()
        }
    }

    private fun ExoPlayer.rewind() {
        val newPosition = (currentPosition - 10_000).coerceAtLeast(0)
        seekTo(newPosition)
    }

    private fun ExoPlayer.forward() {
        val newPosition = (currentPosition + 10_000).coerceAtMost(duration)
        seekTo(newPosition)
    }

    private fun ExoPlayer.playNext() {
        if (hasNextMediaItem()) {
            val nextIndex = currentMediaItemIndex + 1
            val mediaItemId = getMediaItemAt(nextIndex)
            val seekPosition = hashMapVideoStates[mediaItemId.mediaId]?.currentPosition ?: 0
            seekTo(nextIndex, seekPosition)
        }
    }

    private fun ExoPlayer.playPrevious() {
        if (isCommandAvailable(COMMAND_SEEK_TO_MEDIA_ITEM) && hasPreviousMediaItem()) {
            val previousIndex = currentMediaItemIndex - 1
            val mediaItemId = getMediaItemAt(previousIndex)
            val seekPosition = hashMapVideoStates[mediaItemId.mediaId]?.currentPosition ?: 0
            seekTo(previousIndex, seekPosition)
        }
    }
}