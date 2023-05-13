package dev.cherny.kinseedtable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.cherny.kinseedtable.ui.table.Table
import dev.cherny.kinseedtable.ui.table.TableViewModel
import dev.cherny.kinseedtable.ui.theme.KinseedTableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = TableViewModel()
        viewModel.start()

        setContent {
            KinseedTableTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Table(viewModel)
                }
            }
        }
    }
}