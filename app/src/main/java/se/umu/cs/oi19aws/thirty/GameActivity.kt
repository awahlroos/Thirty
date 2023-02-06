package se.umu.cs.oi19aws.thirty

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class GameActivity : AppCompatActivity(){

    //Måste spara:
    // - KLAR Rounds left variabel
    // - KLAR Throws left variabel
    // choiceList, och vilka som är tagna och inte
    // Throwbutton state

    private val choiceList = ArrayList<AppCompatButton>()
    private var dieSum = 0
    private var dice = Dice()
    private val diceList = ArrayList<ImageButton>()
    private var counter:Counter = Counter()
    //Create six dice with starting value 0
    private val diceValues = arrayOf(0,0,0,0,0,0)
    private lateinit var roundsLeftTV:TextView
    private lateinit var throwsLeftTV:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        roundsLeftTV = findViewById(R.id.nr_of_rounds_left)
        throwsLeftTV = findViewById(R.id.nr_of_throws_left)

        restoreState(savedInstanceState)

        roundsLeftTV.text = counter.roundCounter.toString()
        throwsLeftTV.text = counter.throwCounter.toString()


        val sumChoiceList = ArrayList<AppCompatButton>()
        val scoreboard = ArrayList<Int>()
        val chosenOption = ArrayList<Int>()

        val restartButton = findViewById<ImageButton>(R.id.restart_button)
        restartButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        //Get throw button
        val throwButton:AppCompatButton = findViewById(R.id.throw_btn)
        throwButton.setOnClickListener{
            //Put this code in function in Dice model
            if(counter.throwCounter > 0) {
                for(die in diceList){
                    if(die.isActivated){
                        val newDieVal = dice.throwDie()
                        diceValues[diceList.indexOf(die)] = newDieVal
                        getWhiteDice(die, newDieVal)
                        die.isActivated = false
                    }
                }
                counter.decreaseThrows()
                throwsLeftTV.text = counter.throwCounter.toString()
            }

            if(counter.throwCounter == 0) {
                //Reset all dice to white color.
                for(die in diceList){
                    getWhiteDice(die, diceValues[diceList.indexOf(die)])
                }
                throwButton.isClickable = false
                throwButton.alpha = .5f
                //activateSumBtns()

                //Move this
                //roundsLeft.text = roundsLeftCounter--.toString()
            }
        }

        //Get dice
        val die1:ImageButton = findViewById(R.id.dice1)
        val die2:ImageButton = findViewById(R.id.dice2)
        val die3:ImageButton = findViewById(R.id.dice3)
        val die4:ImageButton = findViewById(R.id.dice4)
        val die5:ImageButton = findViewById(R.id.dice5)
        val die6:ImageButton = findViewById(R.id.dice6)

        diceList.add(die1)
        diceList.add(die2)
        diceList.add(die3)
        diceList.add(die4)
        diceList.add(die5)
        diceList.add(die6)

        //Generate first throw automatically
        throwAllDice()

        for (die in diceList){
            die.setOnClickListener{
                die.isActivated = !die.isActivated
                if (die.isActivated){
                    getGreyDice(die, diceValues[diceList.indexOf(die)])
                } else {
                    getWhiteDice(die, diceValues[diceList.indexOf(die)])
                }
            }
        }

        val sumLow:AppCompatButton = findViewById(R.id.low_btn)
        val sum4:AppCompatButton = findViewById(R.id.sum4_btn)
        val sum5:AppCompatButton = findViewById(R.id.sum5_btn)
        val sum6:AppCompatButton = findViewById(R.id.sum6_btn)
        val sum7:AppCompatButton = findViewById(R.id.sum7_btn)
        val sum8:AppCompatButton = findViewById(R.id.sum8_btn)
        val sum9:AppCompatButton = findViewById(R.id.sum9_btn)
        val sum10:AppCompatButton = findViewById(R.id.sum10_btn)
        val sum11:AppCompatButton = findViewById(R.id.sum11_btn)
        val sum12:AppCompatButton = findViewById(R.id.sum12_btn)


        choiceList.add(findViewById(R.id.low_btn))
        choiceList.add(findViewById(R.id.sum4_btn))
        choiceList.add(findViewById(R.id.sum5_btn))
        choiceList.add(findViewById(R.id.sum6_btn))
        choiceList.add(findViewById(R.id.sum7_btn))
        choiceList.add(findViewById(R.id.sum8_btn))
        choiceList.add(findViewById(R.id.sum9_btn))
        choiceList.add(findViewById(R.id.sum10_btn))
        choiceList.add(findViewById(R.id.sum11_btn))
        choiceList.add(findViewById(R.id.sum12_btn))

        //deactivateSumBtns()


        val mapChoiceToInt: Map<AppCompatButton, Int> = mapOf(
            sumLow to 3,
            sum4 to 4,
            sum5 to 5,
            sum6 to 6,
            sum7 to 7,
            sum8 to 8,
            sum9 to 9,
            sum10 to 10,
            sum11 to 11,
            sum12 to 12
        )


        /*val mapDieToInt: Map<ImageButton, Int> = mapOf(
            die1 to 1,
            die2 to 2,
            die3 to 3,
            die4 to 4,
            die5 to 5,
            die6 to 6
        )

        val mapIntToDie: Map<Int, ImageButton> = mapOf(
            1 to die1,
            2 to die2,
            3 to die3,
            4 to die4,
            5 to die5,
            6 to die6
        )*/

        //Get sum choice
        sumChoiceList.add(findViewById(R.id.low_btn))
        sumChoiceList.add(findViewById(R.id.sum4_btn))
        sumChoiceList.add(findViewById(R.id.sum5_btn))
        sumChoiceList.add(findViewById(R.id.sum6_btn))
        sumChoiceList.add(findViewById(R.id.sum7_btn))
        sumChoiceList.add(findViewById(R.id.sum8_btn))
        sumChoiceList.add(findViewById(R.id.sum9_btn))
        sumChoiceList.add(findViewById(R.id.sum10_btn))
        sumChoiceList.add(findViewById(R.id.sum11_btn))
        sumChoiceList.add(findViewById(R.id.sum12_btn))

        for(button in sumChoiceList) {
            button.setOnClickListener{
                var validDice = true
                for (die in diceList){
                    //If LOW is chosen
                    if(mapChoiceToInt[button]!! == 3) {
                        if(die.isActivated && diceValues[diceList.indexOf(die)] <= 3) {
                            dieSum += diceValues[diceList.indexOf(die)]
                        } else if(die.isActivated && diceValues[diceList.indexOf(die)] > 3){
                            //A die with value >3 is chosen
                            validDice = false
                        }
                    }
                    //Else get value of chosen dice
                    else if(die.isActivated) {
                        dieSum += diceValues[diceList.indexOf(die)]
                    }
                }
                Log.d("TAG", "dieSum: $dieSum")

                //If a correct sum is chosen
                if(dice.checkSumChoice(mapChoiceToInt[button]!!, dieSum) && validDice) {
                    //Add this to a function
                    scoreboard.add(dieSum)
                    chosenOption.add(mapChoiceToInt[button]!!)
                    button.isClickable = false
                    button.alpha = .25f
                    throwButton.isClickable = true
                    throwButton.alpha = 1f
                    counter.decreaseRounds()
                    roundsLeftTV.text = counter.roundCounter.toString()
                    setDiceActivatedFalse()
                    throwAllDice()
                    counter.resetThrows()
                    throwsLeftTV.text = counter.throwCounter.toString()
                    //Gå till nästa omgång
                    Log.d("TAG", "Added: " + scoreboard.last().toString() + " to scoreboard").toString()
                    Log.d("TAG", "Rounds left: $counter")
                }
                //Reset dieSum
                dieSum = 0

                if(counter.roundCounter == 0){
                    Log.d("TAG", "roundsLeftCounter== 0")
                    val i = Intent(this, ResultsActivity::class.java)
                    i.putExtra("score", scoreboard)
                    i.putExtra("chosenOption", chosenOption)
                    startActivity(i)
                    //setResult(Activity.RESULT_OK, i)
                    finish()
                }

                //Log.d("TAG", dice.checkSumChoice(mapChoiceToInt[button]!!, 6).toString())
            }
        }

        //button.alpha = .5f;
        //button.isClickable = false;
    }

    private fun getWhiteDice(die:ImageView, value:Int){
        when(value) {
            1 -> die.setImageResource(R.drawable.white1)
            2 -> die.setImageResource(R.drawable.white2)
            3 -> die.setImageResource(R.drawable.white3)
            4 -> die.setImageResource(R.drawable.white4)
            5 -> die.setImageResource(R.drawable.white5)
            6 -> die.setImageResource(R.drawable.white6)
        }
    }

    private fun getGreyDice(die:ImageView, value:Int){
        when(value) {
            1 -> die.setImageResource(R.drawable.grey1)
            2 -> die.setImageResource(R.drawable.grey2)
            3 -> die.setImageResource(R.drawable.grey3)
            4 -> die.setImageResource(R.drawable.grey4)
            5 -> die.setImageResource(R.drawable.grey5)
            6 -> die.setImageResource(R.drawable.grey6)
        }
    }

    /*
    private fun activateSumBtns(){
        for(btn in testArr) {
            btn.isClickable = true
            btn.alpha = 1f
        }
    }

    private fun deactivateSumBtns(){
        for(btn in testArr) {
            btn.isClickable = false
            btn.alpha = .25f
        }
    }
    */

    private fun restoreState(savedInstanceState: Bundle?) {
        if(savedInstanceState != null){
            counter = savedInstanceState.getParcelable(COUNTER_KEY)!!
            //throwsLeftCounter = savedInstanceState.getParcelable(THROWS_COUNTER_KEY)
        }
    }

    private fun setDiceActivatedFalse(){
        for(die in diceList){
            die.isActivated = false
        }
    }

    private fun throwAllDice(){
        for(die in diceList){
            val newDieVal = dice.throwDie()
            diceValues[diceList.indexOf(die)] = newDieVal
            getWhiteDice(die, newDieVal)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //val roundsLeft = roundsLeftCounter
        outState.putParcelable(COUNTER_KEY, counter)
    }

    /*
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val roundsLeft = savedInstanceState.getParcelable(
            "SavedRoundsLeft", RoundCounter)
        roundsLeftCounter = roundsLeft
        roundsLeftTV.text = roundsLeftCounter.toString()
    }
    */

    companion object {
        private const val COUNTER_KEY = "GameActivity.Counter"
    }
}