package ir.mrhib.trixcounter.ui.tableActivity

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.mrhib.clinicbookingsystem.ui.dialogs.CustomDialog
import ir.mrhib.trixcounter.R
import ir.mrhib.trixcounter.model.Game
import ir.mrhib.trixcounter.ui.theme.ThemeColors
import ir.mrhib.trixcounter.ui.theme.vazir


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableScreen(viewModel: TableActivityViewModel = hiltViewModel()) {
    val games by viewModel.allGames.observeAsState(listOf())
    val points = remember { mutableStateOf(IntArray(4)) }
    points.value[0] = games.sumOf { it.pointP1 }
    points.value[1] = games.sumOf { it.pointP2 }
    points.value[2] = games.sumOf { it.pointP3 }
    points.value[3] = games.sumOf { it.pointP4 }
    val column1Weight = .1f
    val column2Weight = .2f
    val context = LocalContext.current
    val showDeleteDialog = remember { mutableStateOf(false) }
    val gameToDelete = remember { mutableStateOf(Game(-1, -1, -1, -1, -1, -1, -1, -1)) }
    if (showDeleteDialog.value) {
        CustomDialog(
            title = context.resources.getString(R.string.delete_game),
            subTitle = context.resources.getString(R.string.delete_question),
            text = "",
            setShowDialog = { showDeleteDialog.value = it },
            icon = Icons.Filled.Warning,
            iconBackgroundColor = Color.Red,
            submitButtonText = context.resources.getString(R.string.yes_delete),
            dismissButtonText = context.resources.getString(R.string.cancel),
            onSubmit = {
                if (gameToDelete.value.id != -1) {
                    viewModel.deleteGame(gameToDelete.value)
                    showDeleteDialog.value = false
                    players[gameToDelete.value.king].contracts.add(CONTRACTS.values()[gameToDelete.value.contract])
                }
            }) {
            showDeleteDialog.value = false
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(15.dp))
        ) {
//            item {
                stickyHeader {
                    Row(
                        Modifier
                            .background(Color.LightGray, RoundedCornerShape(15.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TableCell(text = " ", weight = column1Weight)
                        TableCell(text = " ", weight = 0.05f)
                        TableCell(
                            text = playersNames[0],
                            weight = column2Weight,
                            17,
                            color = Color.Black
                        )
                        TableCell(
                            text = playersNames[1],
                            weight = column2Weight,
                            17,
                            color = Color.Black
                        )
                        TableCell(
                            text = playersNames[2],
                            weight = column2Weight,
                            17,
                            color = Color.Black
                        )
                        TableCell(
                            text = playersNames[3],
                            weight = column2Weight,
                            17,
                            color = Color.Black
                        )
                    }
                    Row(
                        Modifier
                            .background(Color(0xFFD3D3D3), RoundedCornerShape(15.dp))
                            .offset(0.dp, (-5).dp)
                            .height(30.dp)
                            .wrapContentHeight(),
//                            .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TableCell(text = " ", weight = column1Weight)
                        TableCell(text = " ", weight = 0.05f)
                        TableCell(
                            text = points.value[0].toString(),
                            weight = column2Weight,
                            30,
                            color = getColor(points.value, points.value[0])
                        )
                        TableCell(
                            text = points.value[1].toString(),
                            weight = column2Weight,
                            30,
                            color = getColor(points.value, points.value[1])
                        )
                        TableCell(
                            text = points.value[2].toString(),
                            weight = column2Weight,
                            30,
                            color = getColor(points.value, points.value[2])
                        )
                        TableCell(
                            text = points.value[3].toString(),
                            weight = column2Weight,
                            30,
                            color = getColor(points.value, points.value[3])
                        )
                    }
                }
//            }
//            itemsIndexed(games.reversed()){index: Int, it: Game ->
//
//            }

            itemsIndexed(games.reversed()) { index: Int, it: Game ->
                players[it.king].contracts.remove(CONTRACTS.values()[it.contract])
                Row(
                    Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {},
                            onLongClick = {
                                gameToDelete.value = it
                                showDeleteDialog.value = true
                            }
                        ), verticalAlignment = Alignment.CenterVertically
                ) {
                    TableCell(text = "${(games.size - index)}", weight = 0.05f)
                    when (it.contract) {
                        0 -> TableCell(vector = CONTRACTS.KING_OF_HEARTS.vector, column1Weight)
                        1 -> TableCell(vector = CONTRACTS.DIAMONDS.vector, column1Weight)
                        2 -> TableCell(vector = CONTRACTS.QUEENS.vector, column1Weight)
                        3 -> TableCell(vector = CONTRACTS.COLLECTIONS.vector, column1Weight)
                        4 -> TableCell(vector = CONTRACTS.TREX.vector, column1Weight)
                    }

                    TableCell(
                        text = it.pointP1.toString(),
                        weight = column2Weight,
                        contractPlayer = it.king == 0
                    )
                    TableCell(
                        text = it.pointP2.toString(),
                        weight = column2Weight,
                        contractPlayer = it.king == 1
                    )
                    TableCell(
                        text = it.pointP3.toString(),
                        weight = column2Weight,
                        contractPlayer = it.king == 2
                    )
                    TableCell(
                        text = it.pointP4.toString(),
                        weight = column2Weight,
                        contractPlayer = it.king == 3
                    )
                }
            }
        }
    }
}


@Composable
fun RowScope.TableCell(
    vector: Int, weight: Float
) {
    Box(
        modifier = Modifier.weight(weight), contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(vector), contentDescription = "",
            Modifier.width(25.dp),
        )
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    fontSize: Int = 17,
    contractPlayer: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary,
    style: TextStyle? = null
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .height(40.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            Modifier.wrapContentHeight(),
            fontFamily = vazir,
            fontSize = fontSize.sp,
            color = if (contractPlayer) MaterialTheme.colorScheme.tertiary else color,
            style = style ?: TextStyle(fontSize = fontSize.sp)
        )
    }
}


@Composable
fun getColor(points: IntArray, point: Int): Color {
    return when (points.sortedArray().indexOf(point)) {
        0 -> Color.Red
        1 -> if (isSystemInDarkTheme()) Color(0xFFFFC329) else Color(0xFFB48100)
        2 -> Color.Black
        else -> if (isSystemInDarkTheme()) ThemeColors.Night.text else ThemeColors.Day.text
    }
}
