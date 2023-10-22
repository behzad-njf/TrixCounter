package ir.mrhib.trixcounter.ui.tableActivity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import ir.mrhib.clinicbookingsystem.ui.dialogs.CustomDialogWithCustomContent
import ir.mrhib.trixcounter.R
import ir.mrhib.trixcounter.model.Game
import ir.mrhib.trixcounter.model.Player
import ir.mrhib.trixcounter.ui.theme.TrixCounterTheme
import ir.mrhib.trixcounter.ui.theme.titr
import ir.mrhib.trixcounter.ui.theme.vazir
import kotlinx.coroutines.delay
import kotlin.math.abs

private var matchId: Int = 0
lateinit var playersNames: ArrayList<String>
lateinit var players: ArrayList<Player>

@AndroidEntryPoint
class TableActivity : ComponentActivity() {
    private val viewModel: TableActivityViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            matchId = it.getLongExtra("MatchID", 0).toInt()
            viewModel.matchId.value = matchId
            playersNames = arrayListOf()
            players = arrayListOf()
            playersNames = intent.getSerializableExtra("Players") as ArrayList<String>
            for (i in playersNames.indices) {
                players.add(
                    Player(
                        i, playersNames[i], arrayListOf(
                            CONTRACTS.KING_OF_HEARTS,
                            CONTRACTS.DIAMONDS,
                            CONTRACTS.QUEENS,
                            CONTRACTS.COLLECTIONS,
                            CONTRACTS.TREX
                        )
                    )
                )
            }
        }

        setContent {
            TrixCounterTheme {
                val extended = remember { mutableStateOf(false) }
                val showNewHandDialog = remember { mutableStateOf(false) }
                val thePlayerWhoSetTheContract = remember { mutableStateOf(0) }

                if (showNewHandDialog.value) {
                    NewHandDialog(showNewHandDialog, thePlayerWhoSetTheContract)
                }

                Scaffold(topBar = { }, floatingActionButton = {
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        FloatingActionButton(onClick = {
//                            extended.value = !extended.value
//                        }, content = {
//                            Icon(Icons.Filled.List, "Table")
//                        }, containerColor = Color(0xD8A8A8A8), contentColor = Color(0xFF000000)
//                        )
//                        Spacer(modifier = Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        if (extended.value) {
                            players.forEach {
                                ExtendedFloatingActionButton(text = {
                                    Text(
                                        text = it.name,
                                        color = Color.White,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }, icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Person,
                                        contentDescription = it.name,
                                        tint = Color.White,
                                    )
                                }, onClick = {
                                    showNewHandDialog.value = true
                                    extended.value = false
                                    thePlayerWhoSetTheContract.value = it.id
                                }, expanded = true, containerColor = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }

                        FloatingActionButton(
                            onClick = {
                                extended.value = !extended.value
                            },
                            content = {
                                Icon(Icons.Filled.Create, "Create a new Service")
                            },
                            containerColor = Color(0xD8A8A8A8),
                            contentColor = Color(0xFF000000)
                        )
                    }
//                    }

                }, content = {
                    Surface(
                        modifier = Modifier.padding(it)
                    ) {
                        TableScreen()
                    }
                })
            }
            BackPressSample()
        }
    }

    private fun addKingOfHeartsHand(playerIndex: Int, contractPlayer: Int) {
        viewModel.insertNewGame(
            Game(
                matchId = matchId,
                king = contractPlayer,
                contract = CONTRACTS.KING_OF_HEARTS.ordinal,
                pointP1 = if (playerIndex == 0) -75 else 0,
                pointP2 = if (playerIndex == 1) -75 else 0,
                pointP3 = if (playerIndex == 2) -75 else 0,
                pointP4 = if (playerIndex == 3) -75 else 0
            )
        )
    }

    private fun insertNewTrixGame(orders: SnapshotStateList<String>, contractPlayer: Int) {
        val tmpList = orders.reversed()
        viewModel.insertNewGame(
            Game(
                matchId = matchId,
                king = contractPlayer,
                contract = CONTRACTS.TREX.ordinal,
                pointP1 = (tmpList.indexOf(playersNames[0]) + 1) * 50,
                pointP2 = (tmpList.indexOf(playersNames[1]) + 1) * 50,
                pointP3 = (tmpList.indexOf(playersNames[2]) + 1) * 50,
                pointP4 = (tmpList.indexOf(playersNames[3]) + 1) * 50,
            )
        )
    }

    @Composable
    fun TrexDialog(showDialog: MutableState<Boolean>, contractPlayer: Int) {
        val orders = remember { mutableStateListOf<String>() }
        CustomDialogWithCustomContent(
            { showDialog.value = it }, icon = ImageVector.vectorResource(id = R.drawable.joker)
        ) {
            Column(
                modifier = Modifier.wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = resources.getString(R.string.trex),
                    modifier = Modifier.padding(5.dp),
                    textAlign = TextAlign.Center
                )
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.gold),
                        contentDescription = "GOLD",
                        modifier = Modifier.width(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Image(
                        painter = painterResource(id = R.drawable.silver),
                        contentDescription = "SILVER",
                        modifier = Modifier.width(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.bronze),
                        contentDescription = "BRONZE",
                        modifier = Modifier.width(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Image(
                        painter = painterResource(id = R.drawable.fourth),
                        contentDescription = "FOURTH",
                        modifier = Modifier.width(30.dp)
                    )
                }
                Column(modifier = Modifier.border(1.dp, Color.LightGray)) {
                    playersNames.forEach {
                        TextButton(
                            onClick = {
                                if (!orders.contains(it)) orders.add(it)
                            },
                        ) {
                            Row(verticalAlignment = CenterVertically) {
                                Row(modifier = Modifier.weight(0.5f)) {
                                    Icon(
                                        imageVector = Icons.Filled.Person, contentDescription = it
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = it, fontFamily = vazir, fontSize = 18.sp
                                    )
                                }
                                Spacer(modifier = Modifier.weight(0.1f))
                                if (orders.contains(it)) {
                                    val drawable: Int = when (orders.indexOf(it)) {
                                        0 -> R.drawable.gold
                                        1 -> R.drawable.silver
                                        2 -> R.drawable.bronze
                                        else -> {
                                            R.drawable.fourth
                                        }
                                    }
                                    Image(
                                        painter = painterResource(id = drawable),
                                        contentDescription = "GOLD",
                                        modifier = Modifier.width(30.dp)
                                    )
                                }
                            }
                        }
                        Divider(thickness = 1.dp, color = Color.LightGray)

                    }
                }
                Row {
                    IconButton(
                        onClick = {
                            orders.clear()
                        }, modifier = Modifier.weight(0.5f)
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = resources.getString(R.string.reset)
                            )
                            Text(text = resources.getString(R.string.reset))
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            insertNewTrixGame(orders, contractPlayer)
                            showDialog.value = false
                        }, modifier = Modifier.weight(0.5f), enabled = orders.size == 4
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = resources.getString(R.string.save)
                            )
                            Text(text = resources.getString(R.string.save))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun KingOfHeartsDialog(showDialog: MutableState<Boolean>, contractPlayer: Int) {
        CustomDialogWithCustomContent(
            { showDialog.value = it },
            icon = ImageVector.vectorResource(id = R.drawable.king_of_heart)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = resources.getString(R.string.dialog_description_king_of_hearts),
                    modifier = Modifier.padding(5.dp),
                    textAlign = TextAlign.Center
                )
                Divider(thickness = 1.dp, color = Color.LightGray)
                players.forEach {
                    IconButton(
                        onClick = {
                            addKingOfHeartsHand(it.id, contractPlayer)
                            showDialog.value = false
                        }, modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                            Text(text = it.name, fontFamily = vazir)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun QueensDialog(showDialog: MutableState<Boolean>, contractPlayer: Int, contract: CONTRACTS) {
        val points = remember { mutableStateListOf(0, 0, 0, 0) }
        var trickPoint = 75
        var trickCount = 4
        when (contract) {
            CONTRACTS.QUEENS -> {
                trickPoint = 25
                trickCount = 4
            }

            CONTRACTS.COLLECTIONS -> {
                trickPoint = 15
                trickCount = 13
            }

            CONTRACTS.DIAMONDS -> {
                trickPoint = 10
                trickCount = 13
            }

            else -> {

            }
        }
        CustomDialogWithCustomContent(
            { showDialog.value = it }, icon = ImageVector.vectorResource(
                id = when (contract) {
                    CONTRACTS.QUEENS -> R.drawable.queens
                    CONTRACTS.COLLECTIONS -> R.drawable.trix
                    else -> R.drawable.diamond_ace
                }
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = when (contract) {
                        CONTRACTS.QUEENS -> resources.getString(R.string.dialog_description_queens)
                        CONTRACTS.COLLECTIONS -> resources.getString(R.string.dialog_description_collections)
                        else -> resources.getString(R.string.dialog_description_diamonds)
                    }, modifier = Modifier.padding(5.dp), textAlign = TextAlign.Center,
                    fontFamily = vazir
                )
                Divider(thickness = 1.dp, color = Color.LightGray)
                Column {
                    players.forEach {
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                            Text(
                                text = it.name,
                                modifier = Modifier.weight(0.5f),
                                fontFamily = vazir
                            )
                            Box(
                                contentAlignment = Center, modifier = Modifier.weight(0.2f)
                            ) {
                                Text(
                                    text = points[it.id].toString(),
                                    fontFamily = titr,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center
                                )
                            }
                            IconButton(
                                onClick = {
                                    points[it.id] = points[it.id] + trickPoint
                                }, enabled = points[it.id] < 0
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "",
                                    tint = Color.Black
                                )
                            }
                            Text(
                                text = "${abs(points[it.id] / trickPoint)}",
                                fontFamily = vazir,
                                color = MaterialTheme.colorScheme.primary
                            )

                            IconButton(
                                onClick = {
                                    points[it.id] = points[it.id] - trickPoint
                                },
                                enabled = points.sum() > (trickPoint * trickCount) * -1 && points[it.id] > (trickPoint * trickCount) * -1
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
                Divider(thickness = 1.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        viewModel.insertNewGame(
                            Game(
                                matchId = matchId,
                                king = contractPlayer,
                                contract = contract.ordinal,
                                pointP1 = points[0],
                                pointP2 = points[1],
                                pointP3 = points[2],
                                pointP4 = points[3]
                            )
                        )
                        showDialog.value = false
                    }, enabled = when (contract) {
                        CONTRACTS.QUEENS -> points.sum() == -100
                        CONTRACTS.COLLECTIONS -> points.sum() == -195
                        else -> points.sum() == -130
                    }
                ) {
                    Text(
                        text = resources.getString(R.string.save),
                        fontFamily = vazir
                    )
                }
            }
        }
    }


    @Composable
    fun NewHandDialog(showDialog: MutableState<Boolean>, contractPlayer: MutableState<Int>) {
        val kingOfHeartsDialog = remember { mutableStateOf(false) }
        if (kingOfHeartsDialog.value) {
            KingOfHeartsDialog(showDialog, contractPlayer.value)
        }

        val queensDialog = remember { mutableStateOf(false) }
        val contract = remember { mutableStateOf(CONTRACTS.QUEENS) }
        if (queensDialog.value) {
            QueensDialog(showDialog, contractPlayer.value, contract.value)
        }
        val trexDialog = remember { mutableStateOf(false) }
        if (trexDialog.value) {
            TrexDialog(showDialog, contractPlayer.value)
        }

        CustomDialogWithCustomContent(
            { showDialog.value = it }, icon = ImageVector.vectorResource(id = R.drawable.trix)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = resources.getString(R.string.select_the_contract),
                    fontFamily = vazir
                )
                Divider(thickness = 1.dp, color = Color.LightGray)
                Row {
                    IconButton(
                        onClick = {
                            kingOfHeartsDialog.value = true
                        },
                        modifier = Modifier.weight(0.5f),
                        enabled = players[contractPlayer.value].contracts.contains(CONTRACTS.KING_OF_HEARTS)
                    ) {
                        Row(verticalAlignment = CenterVertically) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.king_of_heart),
                                contentDescription = resources.getString(R.string.king_of_hearts)
                            )
                            Text(
                                text = resources.getString(R.string.king_of_hearts),
                                fontFamily = vazir
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            contract.value = CONTRACTS.DIAMONDS
                            queensDialog.value = true
                        },
                        modifier = Modifier.weight(0.5f),
                        enabled = players[contractPlayer.value].contracts.contains(CONTRACTS.DIAMONDS)
                    ) {
                        Row(verticalAlignment = CenterVertically) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.diamond_ace
                                ), contentDescription = resources.getString(R.string.diamonds)
                            )
                            Text(
                                text = resources.getString(R.string.diamonds),
                                fontFamily = vazir
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    IconButton(
                        onClick = {
                            contract.value = CONTRACTS.QUEENS
                            queensDialog.value = true
                        },
                        modifier = Modifier.weight(0.5f),
                        enabled = players[contractPlayer.value].contracts.contains(CONTRACTS.QUEENS)
                    ) {
                        Row(verticalAlignment = CenterVertically) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.queens
                                ), contentDescription = resources.getString(R.string.queens)
                            )
                            Text(
                                text = resources.getString(R.string.queens),
                                fontFamily = vazir
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            contract.value = CONTRACTS.COLLECTIONS
                            queensDialog.value = true
                        },
                        modifier = Modifier.weight(0.5f),
                        enabled = players[contractPlayer.value].contracts.contains(CONTRACTS.COLLECTIONS)
                    ) {
                        Row(verticalAlignment = CenterVertically) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.trix
                                ), contentDescription = resources.getString(R.string.collections)
                            )
                            Text(
                                text = resources.getString(R.string.collections),
                                fontFamily = vazir
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                IconButton(
                    onClick = {
                        trexDialog.value = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = players[contractPlayer.value].contracts.contains(CONTRACTS.TREX)
                ) {
                    Row(verticalAlignment = CenterVertically) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = R.drawable.joker
                            ), contentDescription = resources.getString(R.string.trex)
                        )
                        Text(
                            text = resources.getString(R.string.trex),
                            fontFamily = vazir
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun BackPressSample() {
        var showToast by remember { mutableStateOf(false) }

        var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }
        val context = LocalContext.current

        if (showToast) {
            Toast.makeText(
                context,
                resources.getText(R.string.press_again_to_exit),
                Toast.LENGTH_SHORT
            ).show()
            showToast = false
        }


        LaunchedEffect(key1 = backPressState) {
            if (backPressState == BackPress.InitialTouch) {
                delay(2000)
                backPressState = BackPress.Idle
            }
        }

        BackHandler(backPressState == BackPress.Idle) {
            backPressState = BackPress.InitialTouch
            showToast = true
        }
    }
}

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}


enum class CONTRACTS(val vector: Int) {
    KING_OF_HEARTS(R.drawable.king_of_heart), DIAMONDS(R.drawable.diamond_ace), QUEENS(R.drawable.queens), COLLECTIONS(
        R.drawable.trix
    ),
    TREX(R.drawable.joker)
}