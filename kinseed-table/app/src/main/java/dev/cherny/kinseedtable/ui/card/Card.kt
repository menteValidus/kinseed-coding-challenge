package dev.cherny.kinseedtable.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.cherny.kinseedtable.ui.theme.KinseedTableTheme

data class Card(
    val id: String,
    val firstName: String,
    val lastName: String,
    val createdDate: String,
    val company: String,
    val status: String,
    val fixedLinePhone: String,
    val mobilePhone: String,
    val email: String
)

@Composable
fun Card(modifier: Modifier = Modifier, card: Card) {
    Column(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        DetailedField(details = "ID", value = card.id)
        DetailedField(details = "Name", value = "${card.firstName} ${card.lastName}")
        DetailedField(details = "Created at", value = card.createdDate)
        DetailedField(details = "Company", value = card.company)
        DetailedField(details = "Status", value = card.status)
        DetailedField(details = "Fixed-line phone", value = card.fixedLinePhone)
        DetailedField(details = "Mobile phone", value = card.mobilePhone)
        DetailedField(details = "E-mail", value = card.email)
    }
}

@Composable
private fun DetailedField(
    modifier: Modifier = Modifier,
    details: String,
    value: String
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "$details:",
            fontWeight = FontWeight.SemiBold,
        )
        Text(text = value)
    }
}

@Preview
@Composable
private fun Card_Preview() {
    KinseedTableTheme {
        Card(card = Card(
            id = "id",
            firstName = "firstName",
            lastName = "lastName",
            createdDate = "createdDate",
            company = "company",
            status = "status",
            fixedLinePhone = "fixedLinePhone",
            mobilePhone = "mobilePhone",
            email = "email",
        ))
    }
}