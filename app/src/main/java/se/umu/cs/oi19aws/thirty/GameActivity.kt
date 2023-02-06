package se.umu.cs.oi19aws.thirty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class GameActivity : AppCompatActivity(){

    private var dieSum = 0
    private var dice = Dice()
    private val diceList = ArrayList<ImageButton>()
    private var counter = Counter()
    private var chosenOptions = ChosenOptions()
    private var diceValues = DiceValue()
    private var chosenOption = ArrayList<Int>()
    private var scoreboard = Scoreboard()

    private lateinit var throwButton:AppCompatButton
    private lateinit var sumChoiceList:ArrayList<AppCompatButton>

    private lateinit var roundsLeftTV:TextView
    private lateinit var throwsLeftTV:TextView

    private lateinit var sumLow:AppCompatButton
    private lateinit var sum4:AppCompatButton
    private lateinit var sum5:AppCompatButton
    private lateinit var sum6:AppCompatButton
    private lateinit var sum7:AppCompatButton
    private lateinit var sum8:AppCompatButton
    private lateinit var sum9:AppCompatButton
    private lateinit var sum10:AppCompatButton
    private lateinit var sum11:AppCompatButton
    private lateinit var sum12:AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        sumChoiceList = ArrayList()


        //Get TextView for rounds and throws left
        roundsLeftTV = findViewById(R.id.nr_of_rounds_left)
        throwsLeftTV = findViewById(R.id.nr_of_throws_left)

        //Set number of rounds and throws left
        roundsLeftTV.text = counter.roundCounter.toString()
        throwsLeftTV.text = counter.throwCounter.toString()

        //Get choice buttons
        sumLow = findViewById(R.id.low_btn)
        sum4 = findViewById(R.id.sum4_btn)
        sum5 = findViewById(R.id.sum5_btn)
        sum6 = findViewById(R.id.sum6_btn)
        sum7 = findViewById(R.id.sum7_btn)
        sum8 = findViewById(R.id.sum8_btn)
        sum9 = findViewById(R.id.sum9_btn)
        sum10 = findViewById(R.id.sum10_btn)
        sum11 = findViewById(R.id.sum11_btn)
        sum12 = findViewById(R.id.sum12_btn)

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

        val restartButton = findViewById<ImageButton>(R.id.restart_button)
        restartButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        //Get throw button
        throwButton = findViewById(R.id.throw_btn)
        throwButton.setOnClickListener{
            //Put this code in function in Dice model
            if(counter.throwCounter > 0) {
                for(die in diceList){
                    if(die.isActivated){
                        val newDieVal = dice.throwDie()
                        //diceValues[diceList.indexOf(die)] = newDieVal
                        diceValues.replaceInArray(newDieVal, diceList.indexOf(die))
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
                    getWhiteDice(die, diceValues.diceValueArray[diceList.indexOf(die)])//diceValues[diceList.indexOf(die)])
                }
                throwButton.isClickable = false
                throwButton.alpha = .5f
                //activateSumBtns()

                //Move this
                //roundsLeft.text = roundsLeftCounter--.toString()
            }
        }

        for (die in diceList){
            die.setOnClickListener{
                die.isActivated = !die.isActivated
                if (die.isActivated){
                    getGreyDice(die, diceValues.diceValueArray[diceList.indexOf(die)])
                } else {
                    getWhiteDice(die, diceValues.diceValueArray[diceList.indexOf(die)])
                }
            }
        }

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



        if(!restoreState(savedInstanceState)){
            throwAllDice()
        }


        for(button in sumChoiceList) {

            if(chosenOptions.buttonList.contains(mapChoiceToInt[button])){
                button.alpha = .25f
                button.isEnabled = false
            }

            button.setOnClickListener{
                var validDice = true
                for (die in diceList){
                    //If LOW is chosen
                    if(mapChoiceToInt[button]!! == 3) {
                        if(die.isActivated && diceValues.diceValueArray[diceList.indexOf(die)] <= 3) {
                            dieSum += diceValues.diceValueArray[diceList.indexOf(die)]
                        } else if(die.isActivated && diceValues.diceValueArray[diceList.indexOf(die)] > 3){
                            //A die with value >3 is chosen
                            validDice = false
                        }
                    }
                    //Else get value of chosen dice
                    else if(die.isActivated) {
                        dieSum += diceValues.diceValueArray[diceList.indexOf(die)]
                    }
                }
                Log.d("TAG", "dieSum: $dieSum")

                //If a correct sum is chosen
                if(dice.checkSumChoice(mapChoiceToInt[button]!!, dieSum) && validDice) {
                    //Add this to a function
                    scoreboard.addScore(dieSum)
                    Log.d("TAG", "scoreboard size: ${scoreboard.scores.size}")
                    chosenOption.add(mapChoiceToInt[button]!!)
                    chosenOptions.addChoice(mapChoiceToInt[button]!!)
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
                }
                //Reset dieSum
                dieSum = 0

                if(counter.roundCounter == 0){
                    val i = Intent(this, ResultsActivity::class.java)
                    var arr = ArrayList<Int>()
                    for(score in scoreboard.scores){
                        arr.add(score)
                    }
                    Log.d("TAG", "arr.size: ${arr.size}")
                    i.putExtra("score", arr)
                    i.putExtra("chosenOption", chosenOptions.buttonList)
                    startActivity(i)
                    //setResult(Activity.RESULT_OK, i)
                    finish()
                }
            }
        }
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

    private fun setDiceActivatedFalse(){
        for(die in diceList){
            die.isActivated = false
        }
    }

    private fun throwAllDice(){
        for(die in diceList){
            val newDieVal = dice.throwDie()
            diceValues.replaceInArray(newDieVal, diceList.indexOf(die))
            getWhiteDice(die, newDieVal)
        }
    }

    private fun restoreState(savedInstanceState: Bundle?) : Boolean {
        if(savedInstanceState != null){
            counter = savedInstanceState.getParcelable(COUNTER_KEY)!!
            diceValues = savedInstanceState.getParcelable(DICE_VALUE_KEY)!!
            chosenOptions = savedInstanceState.getParcelable(CHOSEN_OPTIONS_KEY)!!
            scoreboard = savedInstanceState.getParcelable(SCOREBOARD_KEY)!!

            roundsLeftTV.text = counter.roundCounter.toString()
            throwsLeftTV.text = counter.throwCounter.toString()

            if(counter.throwCounter == 0){
                throwButton.alpha = .25f
                throwButton.isClickable = false
            }

            diceList.forEachIndexed { index, imageButton ->
                getWhiteDice(imageButton, diceValues.diceValueArray[index])
            }

            /*
            val mapIntToChoice: Map<Int, AppCompatButton> = mapOf(
                3 to sumLow,
                4 to sum4,
                5 to sum5,
                6 to sum6,
                7 to sum7,
                8 to sum8,
                9 to sum9,
                10 to sum10,
                11 to sum11,
                12 to sum12,
            )

            //TODO: Dubblettkod
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
            */

            /*for(choice in sumChoiceList){
                if(chosenOptions.buttonList.contains(mapChoiceToInt[choice])){
                    choice.alpha = .0f
                    choice.isClickable = false
                    Log.d("TAG", "Clickckckckckkc")
                }
            }*/
            return true
        }
        return false
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //val roundsLeft = roundsLeftCounter
        outState.putParcelable(COUNTER_KEY, counter)
        outState.putParcelable(DICE_VALUE_KEY, diceValues)
        outState.putParcelable(CHOSEN_OPTIONS_KEY, chosenOptions)
        outState.putParcelable(SCOREBOARD_KEY, scoreboard)
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
        private const val DICE_VALUE_KEY = "GameActivity.DiceValue"
        private const val CHOSEN_OPTIONS_KEY = "GameActivity.ChosenOptions"
        private const val SCOREBOARD_KEY = "GameActivity.Scoreboard"
    }
}