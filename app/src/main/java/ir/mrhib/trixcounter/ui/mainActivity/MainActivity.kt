package ir.mrhib.trixcounter.ui.mainActivity

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.mrhib.trixcounter.R
import ir.mrhib.trixcounter.ui.components.TextBox
import ir.mrhib.trixcounter.ui.history.HistoryActivity
import ir.mrhib.trixcounter.ui.tableActivity.TableActivity
import ir.mrhib.trixcounter.ui.theme.TrixCounterTheme
import ir.mrhib.trixcounter.ui.theme.vazir
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    //    @Inject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val viewModel:MainActivityViewModel = hiltViewModel()
            viewModel.status.observe(this) {
                it?.let {
                    if (it) {
                        intent = Intent(this, TableActivity::class.java)
                        intent.putExtra("MatchID", viewModel.insertId)
                        intent.putExtra("Players", viewModel.playersNames)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Error :(",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            TrixCounterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }


    @Composable
    @Preview
    fun Content() {
        val showPlayerNameBox = remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = showPlayerNameBox.value,
                modifier = Modifier.align(TopCenter)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.trix), contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .align(TopCenter)
                        .padding(0.dp, 20.dp)
                )
            }
            Column(
                modifier = Modifier
                    .align(TopCenter)
                    .padding(0.dp, 100.dp)
            ) {
                Text(
                    text = resources.getString(R.string.main_title),
                    fontFamily = FontFamily.Cursive,
                    fontSize = 65.sp,
                    textAlign = TextAlign.Center,

                    )
                AnimatedVisibility(
                    visible = !showPlayerNameBox.value,
                    modifier = Modifier.align(CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.trix), contentDescription = "",
                        modifier = Modifier
                            .size(150.dp)
                            .align(CenterHorizontally)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(35.dp)),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                val player1name = remember { mutableStateOf("") }
                val player2name = remember { mutableStateOf("") }
                val player3name = remember { mutableStateOf("") }
                val player4name = remember { mutableStateOf("") }


                TextButton(
                    onClick = { showPlayerNameBox.value = !showPlayerNameBox.value },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Start new match")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = resources.getString(R.string.start_new_match),
                        textAlign = TextAlign.Center,
                        fontFamily = vazir
                    )

                }

                AnimatedVisibility(visible = showPlayerNameBox.value) {
                    PlayersName(player1name, player2name, player3name, player4name)
                }


                TextButton(
                    onClick = {
                        startActivity(Intent(applicationContext, HistoryActivity::class.java))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Rounded.List, contentDescription = "History")
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = resources.getString(R.string.history),
                        textAlign = TextAlign.Center,
                        fontFamily = vazir
                    )

                }

                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Settings")
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = resources.getString(R.string.settings),
                        textAlign = TextAlign.Center,
                        fontFamily = vazir,
                    )

                }
            }
            Column(
                modifier = Modifier
                    .align(BottomCenter)
                    .padding(0.dp, 0.dp, 0.dp, 25.dp)
            ) {
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
                )
                val context = LocalContext.current
                Row(
                    modifier = Modifier.align(CenterHorizontally)
                ) {
                    IconButton(onClick = {
                        localeSelection(context = context, localeTag = Locale("fa").toLanguageTag())
                    }) {
                        Image(painterResource(id = R.drawable.flag_iran), contentDescription = "")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = {
                        localeSelection(context = context, localeTag = Locale("en").toLanguageTag())
                    }) {
                        Image(painterResource(id = R.drawable.flag_uk), contentDescription = "")
                    }
                }
                Text(
                    text = "By : Mr.HiB",
                    modifier = Modifier.align(CenterHorizontally),
                    fontFamily = FontFamily.Monospace
                )
            }


        }
    }

    fun localeSelection(context: Context, localeTag: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(localeTag)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(localeTag)
            )
        }
    }

    @Preview
    @Composable
    fun PreviewPlayerName() {
        val player1name = remember { mutableStateOf("") }
        val player2name = remember { mutableStateOf("") }
        val player3name = remember { mutableStateOf("") }
        val player4name = remember { mutableStateOf("") }
        PlayersName(
            player1name = player1name,
            player2name = player2name,
            player3name = player3name,
            player4name = player4name
        )
    }

    @Composable
    fun PlayersName(
        player1name: MutableState<String>,
        player2name: MutableState<String>,
        player3name: MutableState<String>,
        player4name: MutableState<String>,
    ) {
//    val mainViewModel:MainActivityViewModel = hiltViewModel()


        Column(
            modifier = Modifier
                .padding(5.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TextBox(
                label = resources.getString(R.string.label_player1),
                keyboardType = KeyboardType.Text,
                state = player1name,
                enabled = true
            )
            TextBox(
                label = resources.getString(R.string.label_player2),
                keyboardType = KeyboardType.Text,
                state = player2name,
                enabled = true
            )
            TextBox(
                label = resources.getString(R.string.label_player3),
                keyboardType = KeyboardType.Text,
                state = player3name,
                enabled = true
            )
            TextBox(
                label = resources.getString(R.string.label_player4),
                keyboardType = KeyboardType.Text,
                state = player4name,
                enabled = true
            )
            Spacer(modifier = Modifier.height(20.dp))
//        val viewModel: MainActivityViewModel = hiltViewModel()
            IconButton(
                onClick = {
                    viewModel.createNewGame(
                        if (player1name.value.trim()
                                .isNotEmpty()
                        ) player1name.value else resources.getString(R.string.label_player1),
                        if (player2name.value.trim()
                                .isNotEmpty()
                        ) player2name.value else resources.getString(R.string.label_player2),
                        if (player3name.value.trim()
                                .isNotEmpty()
                        ) player3name.value else resources.getString(R.string.label_player3),
                        if (player4name.value.trim()
                                .isNotEmpty()
                        ) player4name.value else resources.getString(R.string.label_player4),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(Color.DarkGray, RoundedCornerShape(50.dp))
                ) {
                    Spacer(modifier = Modifier.width(25.dp))
                    Text(
                        text = resources.getString(R.string.start),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontFamily = vazir
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = resources.getString(R.string.start_new_match),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}