package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var name1= findViewById<EditText>(R.id.InputFirstName)
        var name2= findViewById<EditText>(R.id.InputSecondName)

        val save = findViewById<Button>(R.id.Commit) //save button

        val modePVP = findViewById<Button>(R.id.PlayersVsPlayerButton)
        val modePVC = findViewById<Button>(R.id.PlayersVsComputerButton)

        var mode = true

        modePVP.setOnClickListener() {
            mode = false
            save.isVisible = true
            name1.isEnabled = true
            name2.isEnabled = true
            name2.setText("Player2")
        }

        modePVC.setOnClickListener() {
            mode = true
            save.isVisible = true
            name1.isEnabled = true
            name2.isEnabled = false
            name2.setText("Autobot")
        }

        save.setOnClickListener(){
            val FirstPlayerName = name1.text.toString()
            val SecondPlayerName = name2.text.toString()

            val intent = Intent(this@MainActivity, ThirdActivity::class.java)

            intent.putExtra("First", FirstPlayerName)
            intent.putExtra("Second", SecondPlayerName)
            intent.putExtra("Mode", mode)

            startActivity(intent)

        }
    }

}