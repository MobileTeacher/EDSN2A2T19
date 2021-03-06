package br.edu.infnet.baseconverter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    val INVALID_ID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        conversor_button.setOnClickListener{
            if (number_edittext.text.isNotEmpty()){
                val inputNumber = number_edittext.text.toInt()

                val inputId = input_radiogroup.checkedRadioButtonId
                val outputId = output_radiogroup.checkedRadioButtonId
                Log.d("MainActivity", "$inputId $outputId")
                if (inputId != INVALID_ID && outputId != INVALID_ID){

                    val inputBase = getBase(inputId)
                    val outputBase = getBase(outputId)

                    val result = convertBase(inputNumber, inputBase, outputBase)

                    result_textview.text = result
                } else {
                    Toast.makeText(this, "Selecione as bases!", Toast.LENGTH_LONG).show()
                }
            } else {
                // da uma resposta visual ao usuário

                number_edittext.error = "Campo vazio!"
            }
        }
    }

    fun getBase(id: Int) = when(id){
        R.id.in_bin, R.id.out_bin -> 2
        R.id.in_oct, R.id.out_oct -> 8
        R.id.in_dec, R.id.out_dec -> 10
        R.id.in_hex, R.id.out_hex -> 16
        else -> 0
    }

    fun convertBase(number: Int, from: Int, to: Int): String {
        if (from == 10 && to < 10){

        }

        if (from == 2 && to == 16){
            return bintoHex(number.toString())
        }


        return number.toString()
    }

    fun decimalToOther(number:Int, base:Int):String{
        val binDigits = mutableListOf<Int>()
        var result = number
        do {
            var rest = result%base
            result = result/base
            binDigits.add(rest)
        } while (result > 0)
        binDigits.reverse()
        return binDigits.joinToString("")
    }

    fun otherToDecimal(numberString:String, base:Int): Int{
        //1572 = 2* 10^0 + 7*10^1 + 5 * 10^2 + 1*10^3
        //val numberString = number.toString()

        var result = 0.0
        for( i in numberString.indices){
            val n = numberString[i].toString().toInt()
            result = result + n*(base.toDouble().pow(numberString.length-i-1))
        }
        return result.toInt()
    }

    fun binToDec(value: String): Int{
        return otherToDecimal(value, 2)
    }

    fun padNumber(value: String): String{
        val rest = value.length%4
        var binTemp = value
        for (i in 0..rest-1){
            binTemp = "0" + binTemp
        }
        return binTemp
    }

    fun bintoHex(value: String): String{
        var binTemp = padNumber(value)
        val q = binTemp.length/4
        var hex = ""
        for (i in 0..q-1){
            val block = binTemp.substring(4*i, 4*i+4)
            val decTemp = binToDec(block)
            hex += when(decTemp){
                10 -> "A"
                11 -> "B"
                12 -> "C"
                13 -> "D"
                14 -> "E"
                15 -> "F"
                else -> decTemp
            }
        }
        return hex

    }

}
