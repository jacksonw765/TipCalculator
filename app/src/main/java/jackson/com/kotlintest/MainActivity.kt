package jackson.com.kotlintest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    var people = 0
    var percent = 0.0
    lateinit var check: String

    lateinit var format: DecimalFormat

    lateinit var pickerPeople: NumberPicker
    lateinit var pickerPercent: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        format = DecimalFormat()

        var textTotal = findViewById<EditText>(R.id.textTotal)
        var textTip = findViewById<EditText>(R.id.textTip)
        var textPerPerson = findViewById<EditText>(R.id.textPricePerson)

        pickerPeople = findViewById<NumberPicker>(R.id.pickerPeople)
        pickerPercent = findViewById<NumberPicker>(R.id.pickerPercent)

        pickerPeople.minValue = 1
        pickerPeople.maxValue = 12
        pickerPeople.value = 1

        pickerPercent.maxValue = 25
        pickerPercent.minValue = 1
        pickerPercent.value = 15

        people = pickerPeople.value
        percent = pickerPercent.value.toDouble();

        pickerPeople.setOnValueChangedListener(object: NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                try {
                    people = p2
                    textTip.setText(format.format(getTip(java.lang.Double.parseDouble(check), percent)))
                    textPerPerson.setText(format.format(getPricePerPerson(java.lang.Double.parseDouble(check), percent, people)))
                    cleanUp()
                } catch (e: Exception) {

                }
            }

        })

        pickerPercent.setOnValueChangedListener(object: NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                errorChecker() //check if there are errors
                try {
                    percent = p2.toDouble()
                    textTip.setText(format.format(getTip(java.lang.Double.parseDouble(check), percent)))
                    textPerPerson.setText(format.format(getPricePerPerson(java.lang.Double.parseDouble(check), percent, people)))
                    cleanUp()
                } catch (e: Exception) {

                }
            }

        })

        textTotal.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                errorChecker()
                check = getString(p0.toString())
                textTip.setText(format.format(getTip(java.lang.Double.parseDouble(check), percent)))
                textPerPerson.setText(format.format(getPricePerPerson(java.lang.Double.parseDouble(check), percent, people)))
                cleanUp()
            }
        })
        errorChecker()
    }

    fun errorChecker() {
        if (people == 0)
            people = pickerPeople.value
        if (percent == 0.0)
            percent = pickerPeople.value.toDouble()
    }

    private fun getTip(checkTotal: Double, percent: Double): Double {
        val percentDouble = percent / 100
        return checkTotal * percentDouble
    }

    fun getPricePerPerson(checkTotal: Double, percent: Double, people: Int): Double {
        val tip = getTip(checkTotal, percent)
        return (checkTotal + tip) / people
    }

    fun getString(sequence: CharSequence): String {
        val builder = StringBuilder(sequence.length)
        builder.append(sequence)
        return builder.toString()
    }

    fun cleanUp() {
        if (textTotal.text.toString() == "") {
            textTip.setText("")
            textPricePerson.setText("")
        }
    }
}


