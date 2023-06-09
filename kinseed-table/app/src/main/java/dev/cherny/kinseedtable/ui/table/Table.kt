package dev.cherny.kinseedtable.ui.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.cherny.kinseedtable.ui.card.Card
import dev.cherny.kinseedtable.ui.theme.KinseedTableTheme
import kotlinx.coroutines.launch

@Composable
fun Table(modifier: Modifier = Modifier, viewModel: TableViewModel) {
    TableImpl(
        modifier = modifier,
        cards = viewModel.cards,
        isFiltersVisible = viewModel.filterVisible,
        filtersState = viewModel.filtersState,
        filterUpdated = viewModel::filterUpdated
    )
}

@Composable
private fun TableImpl(
    modifier: Modifier = Modifier,
    cards: List<Card>,
    isFiltersVisible: Boolean,
    filtersState: FiltersState,
    filterUpdated: (String, String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isFiltersVisible) {
        coroutineScope.launch {
            lazyListState.animateScrollToItem(0)
        }
    }

    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(12.dp),
        state = lazyListState
    ) {
        if (isFiltersVisible) {
            item {
                Filters(
                    modifier = Modifier.padding(bottom = 12.dp),
                    filtersState = filtersState,
                    filterUpdated = filterUpdated,
                )
            }
        }

        for (card in cards) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    card = card
                )
            }
        }
    }
}

typealias FiltersState = Map<String, String>
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun Filters(
    modifier: Modifier = Modifier,
    filtersState: FiltersState,
    filterUpdated: (String, String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        for ((key, value) in filtersState) {
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                    text = "$key:",
                    fontWeight = FontWeight.SemiBold
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide()}
                    ),
                    onValueChange = {
                        filterUpdated(key, it)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun Table_Preview() {
    val card = Card(
        id = "id",
        name = "name",
        createdDate = "createdDate",
        company = "company",
        status = "status",
        fixedLinePhone = "fixedLinePhone",
        mobilePhone = "mobilePhone",
        email = "email",
    )

    KinseedTableTheme {
        TableImpl(
            cards = listOf(card, card, card),
            isFiltersVisible = true,
            filtersState = mutableMapOf(Pair("test", "value")),
            filterUpdated = { _, _ -> }
        )
    }
}