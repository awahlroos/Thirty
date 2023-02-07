package se.umu.cs.oi19aws.thirty.controllers

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import se.umu.cs.oi19aws.thirty.R

/**
 * ResultsActivity
 * Class that is responsible for showing the results for the user.
 */
class ResultsActivity : AppCompatActivity() {

    private val resultList = ArrayList<TextView>()
    private val chosenOptionList = ArrayList<TextView>()
    //Starting index for number of rounds (1-10)
    private var roundNr = 1

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

        val totalPoints = findViewById<TextView>(R.id.total_points)
        val playAgainButton = findViewById<AppCompatButton>(R.id.play_again_button)

        //Set listener on play again button, making it start a new GameActivity to play again
        playAgainButton.setOnClickListener{
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
            finish()
        }

        initRounds()
        fillResultList()
        fillChosenOptionList()

        //Get scoreboard from intent
        val scoreboard = intent.getIntegerArrayListExtra("score") as ArrayList<Int>
        totalPoints.text = scoreboard.sum().toString()

        //Get choice (LOW,4,5...12) for each round from intent
        val chosenOption = intent.getIntegerArrayListExtra("chosenOption") as ArrayList<Int>

        //Set the TextView's text to the score for each round
        resultList.forEachIndexed { index, round ->
            round.text = scoreboard[index].toString()
        }

        chosenOptionList.forEachIndexed { index, _ ->
            if(chosenOption[index] == 3){
                intToTextView(roundNr).text = getString(R.string.rounds, roundNr, "L")
            } else {
                intToTextView(roundNr).text = getString(R.string.rounds, roundNr, chosenOption[index].toString())
            }
            roundNr++
        }
    }

    private fun initRounds(){
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
    }

    private fun fillResultList(){
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
    }

    private fun fillChosenOptionList(){
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
    }

    /**
     * intToTextView(Int)
     * Convert an in (from an ArrayList<Int>) to a TextView in order to set the correct text
     * for the corresponding TextView in the view
     */
    private fun intToTextView(x:Int): TextView {
        return when (x) {
            1 -> round1
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
                val textView = TextView(this)
                textView.text = R.string.round_error.toString()
                return textView
            }
        }
    }
}