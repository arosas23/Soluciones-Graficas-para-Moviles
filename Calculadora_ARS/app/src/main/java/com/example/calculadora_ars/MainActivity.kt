package com.example.calculadora_ars

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var result: TextView
    lateinit var temp: TextView

    var acumulado = 0.0
    var operador = ""
    var hO = false //hay operación
    var resultadoMostrado = false

    val formato = DecimalFormat("0.#######")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        result = findViewById(R.id.result)
        temp = findViewById(R.id.temp)

        // Botones numéricos
        val btnsNumeros = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in btnsNumeros) {
            findViewById<Button>(id).setOnClickListener {
                val texto = (it as Button).text.toString()
                if (result.text.toString() == "0" || hO || resultadoMostrado) {
                    result.text = texto
                    hO = false
                    resultadoMostrado = false
                }
                else {
                    result.text = result.text.toString() + texto
                }
            }
        }

        // Botón Decimal
        findViewById<Button>(R.id.btnPunto).setOnClickListener {
            if (!result.text.toString().contains(".")) {
                result.text = result.text.toString() + "."
            }
        }

        // Operadores
        findViewById<Button>(R.id.btnSuma).setOnClickListener {
            operar("+")
        }
        findViewById<Button>(R.id.btnResta).setOnClickListener {
            operar("-")
        }
        findViewById<Button>(R.id.btnMultiplicacion).setOnClickListener {
            operar("x")
        }
        findViewById<Button>(R.id.btnDivision).setOnClickListener {
            operar("÷")
        }

        // Porcentaje
        findViewById<Button>(R.id.btnPorcentaje).setOnClickListener {
            try {
                val valorActual = result.text.toString().toDouble()

                if (operador.isNotEmpty()) {
                    val porcentaje = when (operador) {
                        "+", "-" -> acumulado * (valorActual / 100)
                        "x", "÷" -> valorActual / 100
                        else -> valorActual / 100
                    }

                    result.text = formato.format(porcentaje)
                }
                else {
                    val porcentaje = valorActual / 100
                    result.text = formato.format(porcentaje)
                }
            }
            catch (e: Exception) {
                result.text = "Error"
            }
        }

        // Igual
        findViewById<Button>(R.id.btnIgual).setOnClickListener {
            calcular()
            operador = ""
            temp.text = ""
            resultadoMostrado = true
        }

        // C (borrar el num actual)
        findViewById<Button>(R.id.btnC).setOnClickListener {
            result.text = "0"
        }

        // CA (borrar all)
        findViewById<Button>(R.id.btnCA).setOnClickListener {
            result.text = "0"
            temp.text = ""
            acumulado = 0.0
            operador = ""
            hO = false
        }
    }

    fun operar(op:String) {
        if (operador.isNotEmpty()) {
            calcular()
        }
        else {
            acumulado = result.text.toString().toDouble()
        }

        operador = op
        temp.text = formato.format(acumulado) + " " + operador
        hO = true
    }

    fun calcular() {
        try {
            val numero = result.text.toString().toDouble()

            acumulado = when (operador) {
                "+" -> acumulado + numero
                "-" -> acumulado - numero
                "x" -> acumulado * numero
                "÷" -> {
                    if (numero == 0.0) {
                        result.text = "Error"
                        return
                    }
                    else {
                        acumulado / numero
                    }
                }
                else -> numero
            }

            result.text = formato.format(acumulado)
        }
        catch (e: Exception) {
            result.text = "Error"
        }
    }

}