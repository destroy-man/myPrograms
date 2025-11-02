package ru.korobeynikov.p39userinputindicationinteractionsource

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

class MyIndicationNode(private val interactionSource: InteractionSource) : Modifier.Node(),
    DrawModifierNode {

    private var isPressed = false

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> isPressed = true
                    is PressInteraction.Release -> isPressed = false
                    is PressInteraction.Cancel -> isPressed = false
                }

                if (isPressed) {
                    launch {
                        animate(
                            (interaction as PressInteraction.Press).pressPosition
                        )
                    }
                }
            }
        }
        super.onAttach()

        /*draw on press
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction ->
                var pressed = false
                when (interaction) {
                    is PressInteraction.Press -> pressed = true
                    is PressInteraction.Release -> pressed = false
                    is PressInteraction.Cancel -> pressed = false
                }

                if (isPressed != pressed) {
                    isPressed = pressed
                    invalidateDraw()
                }
            }
        }
        super.onAttach()
        */
    }

    private val radius = Animatable(0f)
    private var position: Offset? = null

    suspend fun animate(pressPosition: Offset) {
        position = pressPosition
        radius.snapTo(0f)
        radius.animateTo(1f, animationSpec = tween(1500, easing = LinearEasing))
        radius.snapTo(0f)
    }

    override fun ContentDrawScope.draw() {
        //draw animation with brush
        drawContent()
        clipRect {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Green.copy(alpha = 0.2f),
                        Color.Blue.copy(alpha = 0.2f),
                        Color.Red.copy(alpha = 0.2f),
                        Color.Cyan.copy(alpha = 0.2f)
                    ),
                    radius = size.maxDimension,
                    center = position ?: center
                ),
                radius = radius.value * size.maxDimension,
                center = position ?: center
            )
        }

        /*draw animation with color
        drawContent()
        clipRect {
            drawCircle(
                color = Color.Black.copy(alpha = 0.1f),
                radius = radius.value * size.maxDimension,
                center = position ?: center
            )
        }
        */

        /*draw rotate
        rotate(if (isPressed) 5f else 0f) {
            this@draw.drawContent()
        }
        */

        /*draw rectangle
        drawContent()
        if (isPressed) {
            drawRect(Color.Black.copy(alpha = 0.1f))
        }
        */
    }
}

object MyIndicationNodeFactory : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return MyIndicationNode(interactionSource)
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}