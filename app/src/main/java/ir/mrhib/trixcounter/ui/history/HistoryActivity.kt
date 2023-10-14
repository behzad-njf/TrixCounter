package ir.mrhib.trixcounter.ui.history

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ir.mrhib.clinicbookingsystem.ui.dialogs.CustomDialog
import ir.mrhib.trixcounter.R
import ir.mrhib.trixcounter.model.Match
import ir.mrhib.trixcounter.ui.components.TopBar
import ir.mrhib.trixcounter.ui.tableActivity.TableActivity
import ir.mrhib.trixcounter.ui.theme.TrixCounterTheme
import java.util.Date

@AndroidEntryPoint
class HistoryActivity : ComponentActivity() {
    private val viewModel: HistoryViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrixCounterTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = { TopBar(title = resources.getString(R.string.title_activity_history)) },
                    content = {
                        Surface(
                            modifier = Modifier.padding(it)
                        ) {
                            val matches = viewModel.allMatches.observeAsState(listOf())
                            LazyColumn(
                                Modifier
                                    .fillMaxSize()
                                    .padding(5.dp)
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(15.dp))
                            ) {

                                items(matches.value) {
                                    ListItem(it)
                                }
                            }
                        }
                    })
            }
        }
    }

    //    @Preview
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ListItem(match: Match) {
        val datetimeString =
            DateFormat.format("dd-MM-yyyy HH:mm", Date(match.createdDate)).toString()
        val timeString = datetimeString.substring(11)
        val dateString = datetimeString.substring(0, 10)
        val context = LocalContext.current
        val showDeleteDialog = remember { mutableStateOf(false) }
        if (showDeleteDialog.value) {
            CustomDialog(
                title = context.resources.getString(R.string.delete_match),
                subTitle = context.resources.getString(R.string.delete_match_question),
                text = "",
                setShowDialog = { showDeleteDialog.value = it },
                icon = Icons.Filled.Warning,
                iconBackgroundColor = Color.Red,
                submitButtonText = context.resources.getString(R.string.yes_delete),
                dismissButtonText = context.resources.getString(R.string.cancel),
                onSubmit = {
                    viewModel.deleteMatch(match)
                    showDeleteDialog.value = false
                }) {
                showDeleteDialog.value = false
            }
        }

        Box(modifier = Modifier
            .padding(5.dp)
            .combinedClickable(
                onClick = {
                    val playersNames: ArrayList<String> = ArrayList()
                    playersNames.add(match.player1)
                    playersNames.add(match.player2)
                    playersNames.add(match.player3)
                    playersNames.add(match.player4)
                    intent = Intent(this, TableActivity::class.java)
                    intent.putExtra("MatchID", match.matchId.toLong())
                    intent.putExtra("Players", playersNames)
                    startActivity(intent)
                },
                onLongClick = {
                    showDeleteDialog.value = true
                }
            )) {
            Card {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .weight(0.2f)
                    ) {
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                            Text(text = match.player1)
                        }
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                            Text(text = match.player2)
                        }
                    }
                    Column(modifier = Modifier.weight(0.2f)) {
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                            Text(text = match.player3)
                        }
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                            Text(text = match.player4)
                        }
                    }
                    Column(modifier = Modifier.weight(0.2f)) {
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = dateString)
                        }
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.clock_icon),
                                contentDescription = "",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = timeString)
                        }
                    }
                }
            }
        }
    }
}