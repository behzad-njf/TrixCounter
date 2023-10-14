package ir.mrhib.trixcounter.ui.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ir.mrhib.trixcounter.R
import ir.mrhib.trixcounter.ui.components.TopBar
import ir.mrhib.trixcounter.ui.theme.TrixCounterTheme

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
                            val matches = viewModel.allMatches.observeAsState()
                        }
                    })
            }
        }
    }

    @Composable
    fun ListItem() {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(0.2f)) {
                Row {

                }
            }
            Column(modifier = Modifier.weight(0.2f)) {

            }
            Column(modifier = Modifier.weight(0.2f)) {

            }
        }
    }
}