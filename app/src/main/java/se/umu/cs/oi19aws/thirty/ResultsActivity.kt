package se.umu.cs.oi19aws.thirty

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class ResultsActivity : AppCompatActivity() {

    private lateinit var round1:TextView
    private lateinit var round2:TextView
    private lateinit var round3:TextView
    private lateinit var round4:TextView
    private lateinit var round5:TextView
    private lateinit var round6:TextView
    private lateinit var round7:TextView
    private lateinit var round8:TextView
    private lateinit var round9:TextView
    private lateinit var round10:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        var resultList = ArrayList<TextView>()
        var chosenOptionList = ArrayList<TextView>()
        var totalPoints = findViewById<TextView>(R.id.total_points)
        val playAgainButton = findViewById<AppCompatButton>(R.id.play_again_button)

        playAgainButton.setOnClickListener(){
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
            //setResult(Activity.RESULT_OK, i)
            finish()
        }

        var roundNr = 1;

        /*
        val res1 = findViewById<TextView>(R.id.res1)
        val res2 = findViewById<TextView>(R.id.res2)
        val res3 = findViewById<TextView>(R.id.res3)
        val res4 = findViewById<TextView>(R.id.res4)
        val res5 = findViewById<TextView>(R.id.res5)
        val res6 = findViewById<TextView>(R.id.res6)
        val res7 = findViewById<TextView>(R.id.res7)
        val res8 = findViewById<TextView>(R.id.res8)
        val res9 = findViewById<TextView>(R.id.res9)
        val res10 = findViewById<TextView>(R.id.res10)
        */

        round1 = findViewById(R.id.round1)
        round2 = findViewById(R.id.round2)
        round3 = findViewById(R.id.round3)
        round4 = findViewById(R.id.round4)
        round5 = findViewById(R.id.round5)
        round6 = findViewById(R.id.round6)
        round7 = findViewById(R.id.round7)
        round8 = findViewById(R.id.round8)
        round9 = findViewById(R.id.round9)
        round10 = findViewById(R.id.round10)

        resultList.add(findViewById(R.id.res1))
        resultList.add(findViewById(R.id.res2))
        resultList.add(findViewById(R.id.res3))
        resultList.add(findViewById(R.id.res4))
        resultList.add(findViewById(R.id.res5))
        resultList.add(findViewById(R.id.res6))
        resultList.add(findViewById(R.id.res7))
        resultList.add(findViewById(R.id.res8))
        resultList.add(findViewById(R.id.res9))
        resultList.add(findViewById(R.id.res10))

        chosenOptionList.add(findViewById(R.id.opt1))
        chosenOptionList.add(findViewById(R.id.opt2))
        chosenOptionList.add(findViewById(R.id.opt3))
        chosenOptionList.add(findViewById(R.id.opt4))
        chosenOptionList.add(findViewById(R.id.opt5))
        chosenOptionList.add(findViewById(R.id.opt6))
        chosenOptionList.add(findViewById(R.id.opt7))
        chosenOptionList.add(findViewById(R.id.opt8))
        chosenOptionList.add(findViewById(R.id.opt9))
        chosenOptionList.add(findViewById(R.id.opt10))

        val scoreboard = intent.getIntegerArrayListExtra("score") as ArrayList<Int>
        totalPoints.text = scoreboard.sum().toString()

        val chosenOption = intent.getIntegerArrayListExtra("chosenOption") as ArrayList<Int>

        resultList.forEachIndexed { index, round ->
            round.text = scoreboard[index].toString()
        }

        chosenOptionList.forEachIndexed { index, _ ->
            if(chosenOption[index] == 3){
                intToTextView(roundNr).text = getString(R.string.rounds, roundNr, "L")
            } else {
                //Log.d("TAG", getString(R.string.rounds, roundNr, chosenOption[index].toString()))
                intToTextView(roundNr).text = getString(R.string.rounds, roundNr, chosenOption[index].toString())
            }
            roundNr++
        }
    }

    private fun intToTextView(x:Int): TextView {
        return when (x) {
            1 -> return round1
            2 -> round2
            3 -> round3
            4 -> round4
            5 -> round5
            6 -> round6
            7 -> round7
            8 -> round8
            9 -> round9
            10 -> round10
            else -> {
                var textView = TextView(this)
                textView.text = "Round number error:"
                return textView
            }
        }
    }
}