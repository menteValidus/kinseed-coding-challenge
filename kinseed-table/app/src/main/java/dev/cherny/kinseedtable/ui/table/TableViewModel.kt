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
    var filtersState by mutableStateOf<FiltersState>(mapOf(Pair("1", "test1"), Pair("22222222", "test2")))

    lateinit var assetManager: AssetManager

    fun start() {
        parseData()
    }

    fun filterUpdated(key: String, value: String) {
        val filtersStateCopy = filtersState.toMutableMap()
        filtersStateCopy[key] = value
        filtersState = filtersStateCopy.toMap()
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

            cards = result.map {
                return@map Card(
                    id = it.id,
                    name = "${it.firstName} ${it.lastName}",
                    createdDate = "",
                    company = it.company,
                    status = it.status,
                    fixedLinePhone = it.fixedLinePhone,
                    mobilePhone = it.mobilePhone,
                    email = it.email,
                )
            }
        } catch (e: JSONException) {
            Log.e("Json", e.toString())
        }
    }
}
