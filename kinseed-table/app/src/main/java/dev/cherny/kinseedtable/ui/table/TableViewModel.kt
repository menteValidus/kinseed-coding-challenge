package dev.cherny.kinseedtable.ui.table

import android.content.res.AssetManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.cherny.kinseedtable.ui.card.Card
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader

data class Person(
    val id: String,
    val firstName: String,
    val lastName: String,
    val createdDate: String,
    val company: String,
    val status: String,
    val fixedLinePhone: String,
    val mobilePhone: String?,
    val email: String?
)

class TableViewModel: ViewModel() {
    var cards by mutableStateOf<List<Card>>(emptyList())
        private set
    var filtersState by mutableStateOf<FiltersState>(
        mapOf(
            Pair("ID", ""),
            Pair("First Name", ""),
            Pair("Last Name", ""),
            Pair("Company", ""),
            Pair("Status", ""),
            Pair("Fixed-Line Phone", ""),
            Pair("Mobile Phone", ""),
            Pair("E-mail", ""),
        )
    )

    private var people: List<Person> = emptyList()

    lateinit var assetManager: AssetManager

    fun start() {
        parseData()
    }

    fun filterUpdated(key: String, value: String) {
        val filtersStateCopy = filtersState.toMutableMap()
        filtersStateCopy[key] = value
        filtersState = filtersStateCopy.toMap()

        filterData()
    }

    private fun filterData() {
        val activeFilters = filtersState.filter {
            return@filter it.value.isNotEmpty()
        }
        var filteredPeople = people.filter {
            var result = true
            for ((key, value) in activeFilters) {
                result = when(key) {
                    "ID" -> result && it.id.contains(value)
                    "First Name" -> result && it.firstName.contains(value)
                    "Last Name" -> result && it.lastName.contains(value)
                    "Company" -> result && it.company.contains(value)
                    "Status" -> result && it.status.contains(value)
                    "Fixed-Line Phone" -> result && it.fixedLinePhone.contains(value)
                    "Mobile Phone" -> result && it.mobilePhone?.contains(value) ?: false
                    "E-mail" -> result && it.email?.contains(value) ?: false
                    else -> result
                }
            }

            return@filter result
        }

        cards = filteredPeople.map(CardMapper::map)
    }

    private fun parseData() {
        val inputStream = assetManager.open("test-data.json")
        val reader = BufferedReader(inputStream.reader())
        val content = StringBuilder()
        reader.use { reader ->
            var line = reader.readLine()
            while (line != null) {
                content.append(line)
                line = reader.readLine()
            }
        }

        try {
            val result: MutableList<Person> = mutableListOf()
            val obj = JSONObject(content.toString())
            val jsonArray = obj.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray[i] as JSONObject
                val company = (item.get("clientOrganisation") as JSONObject).get("name") as String
                val status = (item.get("fullStatus") as JSONObject).get("statusLabel") as String
                result.add(Person(
                    id = item.get("id") as String,
                    firstName = item.get("firstName") as String,
                    lastName = item.get("lastName") as String,
                    createdDate = item.get("created") as String,
                    company = company,
                    status = status,
                    fixedLinePhone = item.get("fixedLinePhone") as String,
                    mobilePhone = item.get("mobilePhone") as? String,
                    email = item.get("email") as? String,
                ))
            }

            people = result

            cards = people.map(CardMapper::map)
        } catch (e: JSONException) {
            Log.e("Json", e.toString())
        }
    }

    object CardMapper {
        fun map(person: Person): Card {
            return Card(
                id = person.id,
                name = "${person.firstName} ${person.lastName}",
                createdDate = person.createdDate,
                company = person.company,
                status = person.status,
                fixedLinePhone = person.fixedLinePhone,
                mobilePhone = person.mobilePhone,
                email = person.email,
            )
        }
    }
}
