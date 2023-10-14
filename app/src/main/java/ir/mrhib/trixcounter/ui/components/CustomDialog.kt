package ir.mrhib.clinicbookingsystem.ui.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ir.mrhib.trixcounter.ui.theme.titr
import ir.mrhib.trixcounter.ui.theme.vazir

@Preview
@Composable
fun ArcPreview() {
    CustomDialog(
        title = "حذف نوبت",
        "آیا از حذف کاربر مطمعن هستید؟",
        "در صورت حذف کاربر تمامی اطلاعات آن از جمله تاریخچه رزور های آن نیز حذف خواهد شد.",
        setShowDialog = {},
        onSubmit = {},
        onDismiss = {},
        icon = Icons.Filled.Done
    )
}

@Composable
fun CustomDialogWithCustomContent(
    setShowDialog: (Boolean) -> Unit,
    icon: ImageVector,
    iconBackgroundColor: Color = Color(0xFF1AE4FF),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false,
            usePlatformDefaultWidth = false)
    ) {
        CustomArcShape(
            modifier = Modifier
                .padding(10.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            icon = icon,
            fabIconBackgroundColor = iconBackgroundColor
        ) {
            content()
        }
    }
}


@Composable
fun CustomDialog(
    title: String,
    subTitle: String,
    text: String,
    setShowDialog: (Boolean) -> Unit,
    icon: ImageVector,
    iconBackgroundColor: Color = Color(0xFF1AE4FF),
    submitButtonText: String = "تایید",
    onSubmit: () -> Unit,
    dismissButtonText: String = "انصراف",
    onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        CustomArcShape(
            modifier = Modifier
                .padding(10.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            icon = icon,
            fabIconBackgroundColor = iconBackgroundColor
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    title,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontFamily = titr,
                    fontSize = 18.sp,
                    style = TextStyle(textDirection = TextDirection.Rtl)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(subTitle, fontSize = 13.sp, fontFamily = vazir, fontWeight = FontWeight.Bold,
                    style = TextStyle(textDirection = TextDirection.Rtl))
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text,
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    fontFamily = vazir,
                    textAlign = TextAlign.Center,
                    style = TextStyle(textDirection = TextDirection.Rtl)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Text(
                            text = dismissButtonText, fontFamily = titr,
                            color = Color.DarkGray
                        )
                        Icon(
                            imageVector = Icons.Filled.Close, contentDescription = "",
                            tint = Color.DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onSubmit,
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Text(
                            text = submitButtonText, fontFamily = titr,
                            color = Color.DarkGray
                        )
                        Icon(
                            imageVector = Icons.Filled.Done, contentDescription = "",
                            tint = Color.DarkGray
                        )
                    }
                }

            }
        }
    }
}


@Composable
private fun CustomArcShape(
    modifier: Modifier,
    elevation: Dp = 4.dp,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    fabIconBackgroundColor: Color = Color(0xFF1AE4FF),
    icon: ImageVector,
    content: @Composable () -> Unit,
) {

    val diameter = 60.dp
    val radiusDp = diameter / 2

    val cornerRadiusDp = 10.dp

    val density = LocalDensity.current
    val cutoutRadius = density.run { radiusDp.toPx() }
    val cornerRadius = density.run { cornerRadiusDp.toPx() }

    val shape = remember {
        GenericShape { size: Size, _: LayoutDirection ->
            this.roundedRectanglePath(
                size = size,
                cornerRadius = cornerRadius,
                fabRadius = cutoutRadius * 2
            )
        }
    }

    Spacer(modifier = Modifier.height(diameter / 2))
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.padding(10.dp)
    ) {
        FloatingActionButton(
            shape = CircleShape,
            containerColor = fabIconBackgroundColor,
            modifier = Modifier
                .offset(y = -diameter / 5)
                .size(diameter)
                .drawBehind {
                    drawCircle(
                        fabIconBackgroundColor.copy(.5f),
                        radius = 1.3f * size.width / 2
                    )

                    drawCircle(
                        fabIconBackgroundColor.copy(.3f),
                        radius = 1.5f * size.width / 2
                    )

                }
                .align(Alignment.TopCenter),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = icon, contentDescription = "", tint = Color.White.copy(0.5f),
                modifier = Modifier.size(100.dp)
            )
        }

        Surface(
            modifier = modifier,
            shape = shape,
            shadowElevation = elevation,
            color = color,
            contentColor = contentColor
        ) {
            Spacer(modifier = Modifier.height(diameter))
            content()
        }
    }
}


fun Path.roundedRectanglePath(
    size: Size,
    cornerRadius: Float,
    fabRadius: Float,
) {

    val centerX = size.width / 2
    val x0 = centerX - fabRadius * 1.15f
    val y0 = 0f

    // offset of the first control point (top part)
    val topControlX = x0 + fabRadius * .5f
    val topControlY = y0

    // offset of the second control point (bottom part)
    val bottomControlX = x0
    val bottomControlY = y0 + fabRadius

    // first curve
    // set the starting point of the curve (P2)
    val firstCurveStart = Offset(x0, y0)

    // set the end point for the first curve (P3)
    val firstCurveEnd = Offset(centerX, fabRadius * 1f)

    // set the first control point (C1)
    val firstCurveControlPoint1 = Offset(
        x = topControlX,
        y = topControlY
    )

    // set the second control point (C2)
    val firstCurveControlPoint2 = Offset(
        x = bottomControlX,
        y = bottomControlY
    )


    // second curve
    // end of first curve and start of second curve is the same (P3)
    val secondCurveStart = Offset(
        x = firstCurveEnd.x,
        y = firstCurveEnd.y
    )

    // end of the second curve (P4)
    val secondCurveEnd = Offset(
        x = centerX + fabRadius * 1.15f,
        y = 0f
    )

    // set the first control point of second curve (C4)
    val secondCurveControlPoint1 = Offset(
        x = secondCurveStart.x + fabRadius,
        y = bottomControlY
    )

    // set the second control point (C3)
    val secondCurveControlPoint2 = Offset(
        x = secondCurveEnd.x - fabRadius / 2,
        y = topControlY
    )


    // Top left arc
    val radius = cornerRadius * 2

    arcTo(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = radius,
            bottom = radius
        ),
        startAngleDegrees = 180.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )



    lineTo(x = firstCurveStart.x, y = firstCurveStart.y)

    // bezier curve with (P2, C1, C2, P3)
    cubicTo(
        x1 = firstCurveControlPoint1.x,
        y1 = firstCurveControlPoint1.y,
        x2 = firstCurveControlPoint2.x,
        y2 = firstCurveControlPoint2.y,
        x3 = firstCurveEnd.x,
        y3 = firstCurveEnd.y
    )

    // bezier curve with (P3, C4, C3, P4)
    cubicTo(
        x1 = secondCurveControlPoint1.x,
        y1 = secondCurveControlPoint1.y,
        x2 = secondCurveControlPoint2.x,
        y2 = secondCurveControlPoint2.y,
        x3 = secondCurveEnd.x,
        y3 = secondCurveEnd.y
    )

    lineTo(x = size.width - cornerRadius, y = 0f)

    // Top right arc
    arcTo(
        rect = Rect(
            left = size.width - radius,
            top = 0f,
            right = size.width,
            bottom = radius
        ),
        startAngleDegrees = -90.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = 0f + size.width, y = size.height - cornerRadius)

    // Bottom right arc
    arcTo(
        rect = Rect(
            left = size.width - radius,
            top = size.height - radius,
            right = size.width,
            bottom = size.height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = cornerRadius, y = size.height)

    // Bottom left arc
    arcTo(
        rect = Rect(
            left = 0f,
            top = size.height - radius,
            right = radius,
            bottom = size.height
        ),
        startAngleDegrees = 90.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = 0f, y = cornerRadius)
    close()
}