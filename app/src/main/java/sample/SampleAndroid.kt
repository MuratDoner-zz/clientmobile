package sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android"
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Sample().checkMe()
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.main_text).text = hello()

        // get reference to button
        val button = findViewById(R.id.button) as Button
        // set on-click listener
        button.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "Rest Service trial:", Toast.LENGTH_SHORT).show()
            val client = HttpClient()
            getUsers(client)


        }
    }
    @Serializable
    data class User(val id: Int, val email: String)
    @Serializable
    data class Data(val data: List<User>)
    private fun getUsers(client: HttpClient) {
        val url= "https://reqres.in/api/users"
        val json = Json(JsonConfiguration.Stable)

        // Block execution till request is completed
        runBlocking {


            client.get<String>(url)
        }.let {users ->

            val userList = Json.nonstrict.parse(Data.serializer(), users) // b is optional since it has default value

            val lv = findViewById<ListView>(R.id.users)
            val userAdapter = ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, userList.data);

            lv.adapter = userAdapter


        }
    }

}
