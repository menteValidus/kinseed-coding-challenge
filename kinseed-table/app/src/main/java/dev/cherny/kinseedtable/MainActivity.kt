package dev.cherny.kinseedtable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.cherny.kinseedtable.ui.table.Table
import dev.cherny.kinseedtable.ui.table.TableViewModel
import dev.cherny.kinseedtable.ui.theme.KinseedTableTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = TableViewModel()
        viewModel.assetManager = assets
        viewModel.start()

        setContent {
            KinseedTableTheme {
                Scaffold(
                    topBar = {
                        CustomTopAppBar(
                            actionButtonPainter = painterResource(id = R.drawable.ic_filter),
                            actionButtonClicked = viewModel::switchFilterVisibility
                        )
                    },
                    content = {
                        Table(modifier = Modifier.fillMaxSize().padding(it), viewModel = viewModel)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    actionButtonPainter: Painter,
    actionButtonClicked: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Text(stringResource(id = R.string.app_name))
        },
        actions = {
            IconButton(onClick = actionButtonClicked) {
                Icon(
                    modifier = Modifier
                        .width(40.dp)
                        .aspectRatio(1f),
                    painter = actionButtonPainter,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        }
    )
}