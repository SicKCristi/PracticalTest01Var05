// C1

package ro.pub.cs.systems.eim.practicaltest01var05

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var05SecondaryActivity : AppCompatActivity() {
    private lateinit var butoane_apasate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var05_secondary)

        butoane_apasate = findViewById<TextView>(R.id.butoanele_apasate)
        val input1 = intent.getStringExtra("butoane_apasate")
        butoane_apasate.setText(input1)

        val verifyButton = findViewById<Button>(R.id.butonVerify)
        verifyButton.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        val cancelButton = findViewById<Button>(R.id.butonCancel)
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}