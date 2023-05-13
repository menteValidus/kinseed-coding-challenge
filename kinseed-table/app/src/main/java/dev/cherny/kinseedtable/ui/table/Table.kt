package dev.cherny.kinseedtable.ui.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.cherny.kinseedtable.ui.card.Card
import dev.cherny.kinseedtable.ui.theme.KinseedTableTheme

@Composable
fun Table(viewModel: TableViewModel) {
    TableImpl(
        cards = viewModel.cards,
        filtersState = viewModel.filtersState,
        filterUpdated = viewModel::filterUpdated
    )
}

@Composable
private fun TableImpl(
    cards: List<Card>,
    filtersState: FiltersState,
    filterUpdated: (String, String) -> Unit
) {
    LazyColumn(modifier = Modifier
        .background(MaterialTheme.colorScheme.secondary)
        .padding(12.dp)) {
        item {
            Filters(
                modifier = Modifier.padding(bottom = 12.dp),
                filtersState = filtersState,
                filterUpdated = filterUpdated,
                onFiltersApplied = {

                }
            )
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Filters(
    modifier: Modifier = Modifier,
    filtersState: FiltersState,
    filterUpdated: (String, String) -> Unit,
    onFiltersApplied: (FiltersState) -> Unit,
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
        for ((key, value) in filtersState) {
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(CenterVertically),
                    text = "$key:",
                    fontWeight = FontWeight.SemiBold
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
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
        firstName = "firstName",
        lastName = "lastName",
        createdDate = "createdDate",
        company = "company",
        status = "status",
        fixedLinePhone = "fixedLinePhone",
        mobilePhone = "mobilePhone",
        email = "email",
    )

    KinseedTableTheme {
        TableImpl(
            listOf(card, card, card),
            filtersState = mutableMapOf(),
            filterUpdated = { _, _ -> }
        )
    }
}