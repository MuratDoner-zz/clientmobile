package sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking

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
    private fun getUsers(client: HttpClient) {
        val url= "https://reqres.in/api/users"

        // Block execution till request is completed
        runBlocking {


            client.get<String>(url)
        }.let {users ->

            Toast.makeText(this@MainActivity, "USERS JSON:" + users, Toast.LENGTH_SHORT).show()

            print(users)
        }
    }

}
