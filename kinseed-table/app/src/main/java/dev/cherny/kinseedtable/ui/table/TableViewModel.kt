package dev.cherny.kinseedtable.ui.table

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.cherny.kinseedtable.ui.card.Card

class TableViewModel: ViewModel() {
    var cards by mutableStateOf<List<Card>>(emptyList())
        private set

    fun start() {
        cards = listOf(
            Card(
                id = "id1",
                firstName = "firstName1",
                lastName = "lastName1",
                createdDate = "createdDate1",
                company = "company1",
                status = "status1",
                fixedLinePhone = "fixedLinePhone1",
                mobilePhone = "mobilePhone1",
                email = "email1",
            ),
            Card(
                id = "id2",
                firstName = "firstName2",
                lastName = "lastName2",
                createdDate = "createdDate2",
                company = "company2",
                status = "status2",
                fixedLinePhone = "fixedLinePhone2",
                mobilePhone = "mobilePhone2",
                email = "email2",
            ),
            Card(
                id = "id2",
                firstName = "firstName2",
                lastName = "lastName2",
                createdDate = "createdDate2",
                company = "company2",
                status = "status2",
                fixedLinePhone = "fixedLinePhone2",
                mobilePhone = "mobilePhone2",
                email = "email2",
            ),
            Card(
                id = "id2",
                firstName = "firstName2",
                lastName = "lastName2",
                createdDate = "createdDate2",
                company = "company2",
                status = "status2",
                fixedLinePhone = "fixedLinePhone2",
                mobilePhone = "mobilePhone2",
                email = "email2",
            )
        )
    }
}