package ro.pub.cs.systems.eim.practicaltest01var05

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var05MainActivity : AppCompatActivity() {

    private lateinit var sir_butoane: EditText
    private lateinit var numar_butoane_apasate : EditText

    private var butoane_apsate=""
    private var numar=0

    private val intentFilter = IntentFilter()

    // D1

    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Log.d(Constants.BROADCAST_RECEIVER_TAG, it.action.toString())
                Log.d(Constants.BROADCAST_RECEIVER_TAG, it.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA).toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var05_main)

        // B1

        sir_butoane=findViewById<EditText>(R.id.afisare_butoane_apasate)
        numar_butoane_apasate=findViewById<EditText>(R.id.numar_apasare_butoane)

        val apasaButontopleft=findViewById<Button>(R.id.topleft)
        apasaButontopleft.setOnClickListener {
            butoane_apsate+="Top Left"
            butoane_apsate+=","
            numar++
            sir_butoane.setText(butoane_apsate)
            numar_butoane_apasate.setText(numar.toString())
            startServiceIfConditionIsMet(butoane_apsate, numar)
        }

        val apasaButontopright=findViewById<Button>(R.id.topright)
        apasaButontopright.setOnClickListener {
            butoane_apsate+="Top Right"
            butoane_apsate+=","
            numar++
            sir_butoane.setText(butoane_apsate)
            numar_butoane_apasate.setText(numar.toString())
            startServiceIfConditionIsMet(butoane_apsate, numar)
        }

        // Daca se apasa butonul Nord, se adauga la sir Nord si se incrementeaza cu 1
        val pasaButoncenter=findViewById<Button>(R.id.center)
        pasaButoncenter.setOnClickListener {
            butoane_apsate+="Center"
            butoane_apsate+=","
            numar++
            sir_butoane.setText(butoane_apsate)
            numar_butoane_apasate.setText(numar.toString())
            startServiceIfConditionIsMet(butoane_apsate, numar)
        }


        // Daca se apasa butonul Nord, se adauga la sir Nord si se incrementeaza cu 1
        val apasaButonbottomleft=findViewById<Button>(R.id.bottomleft)
        apasaButonbottomleft.setOnClickListener {
            butoane_apsate+="Bottom Left"
            butoane_apsate+=","
            numar++
            sir_butoane.setText(butoane_apsate)
            numar_butoane_apasate.setText(numar.toString())
            startServiceIfConditionIsMet(butoane_apsate, numar)
        }

        // Daca se apasa butonul Nord, se adauga la sir Nord si se incrementeaza cu 1
        val apasaButonbottomright=findViewById<Button>(R.id.bottomright)
        apasaButonbottomright.setOnClickListener {
            butoane_apsate+="Bottom Right"
            butoane_apsate+=","
            numar++
            sir_butoane.setText(butoane_apsate)
            numar_butoane_apasate.setText(numar.toString())
            startServiceIfConditionIsMet(butoane_apsate, numar)
        }

        // C2

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "The activity returned with result OK", Toast.LENGTH_LONG).show()
            }
            else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "The activity returned with result CANCELED", Toast.LENGTH_LONG).show()
            }

            // Vom reseta si elementele
            butoane_apsate=""
            numar=0
            sir_butoane.setText("")
            numar_butoane_apasate.setText("0")
        }

        // La click pe buton, pornește activitatea secundară și trimite valorile din input1 și input2
        val navigateToSecondaryActivityButton = findViewById<Button>(R.id.activitate_secundara)
        navigateToSecondaryActivityButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01Var05SecondaryActivity::class.java)
            intent.putExtra("butoane_apasate", sir_butoane.text.toString())
            activityResultsLauncher.launch(intent)
        }

        Constants.actionTypes.forEach { action ->
            intentFilter.addAction(action)
        }
    }

    // B2

    // Vom salva valorile in elementele de interfata cu celelalte variabile din clasa
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("numar_botoane_apasate",numar.toString())
        outState.putString("butoane_apasate",butoane_apsate)
    }

    // Vom pune inapoi valori in campurile din interfata daca acestea nu sunt 0
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey("numar_puncte_cardinale_selectate") && savedInstanceState.containsKey("coordonate")) {
            numar_butoane_apasate.setText(savedInstanceState.getString("numar_puncte_cardinale_selectate"))
            sir_butoane.setText(savedInstanceState.getString("coordonate"))
            numar = numar_butoane_apasate.text.toString().toInt()
            butoane_apsate= sir_butoane.text.toString()
        }
    }

    private fun startServiceIfConditionIsMet(sir_de_butoane: String, numar_butoane_apasate: Int) {
        if (numar_butoane_apasate < Constants.NUMBER_OF_CLICKS_THRESHOLD) {
            val intent = Intent(applicationContext, PracticalTest01Var05Service::class.java).apply {
                putExtra(Constants.INPUT1, sir_de_butoane)
                putExtra(Constants.INPUT2, numar_butoane_apasate)
            }
            applicationContext.startService(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter)
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(messageBroadcastReceiver)
    }

    override fun onDestroy() {
        val intent = Intent(applicationContext, PracticalTest01Var05Service::class.java)
        applicationContext.stopService(intent)
        super.onDestroy()
    }

}