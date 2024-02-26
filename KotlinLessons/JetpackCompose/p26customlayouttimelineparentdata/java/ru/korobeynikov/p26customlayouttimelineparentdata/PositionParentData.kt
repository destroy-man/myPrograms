package ru.korobeynikov.p26customlayouttimelineparentdata

import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density

class PositionParentData(
    val position: Position,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@PositionParentData
}