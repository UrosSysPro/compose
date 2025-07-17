package net.systemvi.configurator.components.common.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Exit: ImageVector
    get() {
        if (_Exit != null) return _Exit!!

        _Exit = ImageVector.Builder(
            name = "Cross2",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(11.7816f, 4.03157f)
                curveTo(12.0062f, 3.80702f, 12.0062f, 3.44295f, 11.7816f, 3.2184f)
                curveTo(11.5571f, 2.99385f, 11.193f, 2.99385f, 10.9685f, 3.2184f)
                lineTo(7.50005f, 6.68682f)
                lineTo(4.03164f, 3.2184f)
                curveTo(3.80708f, 2.99385f, 3.44301f, 2.99385f, 3.21846f, 3.2184f)
                curveTo(2.99391f, 3.44295f, 2.99391f, 3.80702f, 3.21846f, 4.03157f)
                lineTo(6.68688f, 7.49999f)
                lineTo(3.21846f, 10.9684f)
                curveTo(2.99391f, 11.193f, 2.99391f, 11.557f, 3.21846f, 11.7816f)
                curveTo(3.44301f, 12.0061f, 3.80708f, 12.0061f, 4.03164f, 11.7816f)
                lineTo(7.50005f, 8.31316f)
                lineTo(10.9685f, 11.7816f)
                curveTo(11.193f, 12.0061f, 11.5571f, 12.0061f, 11.7816f, 11.7816f)
                curveTo(12.0062f, 11.557f, 12.0062f, 11.193f, 11.7816f, 10.9684f)
                lineTo(8.31322f, 7.49999f)
                lineTo(11.7816f, 4.03157f)
                close()
            }
        }.build()

        return _Exit!!
    }

private var _Exit: ImageVector? = null