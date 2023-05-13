package dev.cherny.kinseedtable.ui.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.cherny.kinseedtable.ui.card.Card
import dev.cherny.kinseedtable.ui.theme.KinseedTableTheme

@Composable
fun Table(viewModel: TableViewModel) {
    val cards = viewModel.cards
    
    TableImpl(cards = cards)
}

@Composable
private fun TableImpl(cards: List<Card>) {
    LazyColumn(modifier = Modifier.background(MaterialTheme.colorScheme.secondary)) {
        for (card in cards) {
            item {
                Card(
                    modifier = Modifier.padding(12.dp),
                    card = card
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
        TableImpl(listOf(card, card, card))
    }
}