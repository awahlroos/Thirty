package se.umu.cs.oi19aws.thirty.controllers

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import se.umu.cs.oi19aws.thirty.R
import se.umu.cs.oi19aws.thirty.models.*

/**
 * GameActivity
 * Class that acts as the main activity, handling user input when playing the game.
 * */
class GameActivity : AppCompatActivity(){

    private var sumChoiceList = ArrayList<AppCompatButton>()
    private val diceList = ArrayList<ImageButton>()
    private var dice = Dice()
    private var counter = Counter()
    private var chosenOptions = ChosenOptions()
    private var diceValues = DiceValue()
    private var chosenOption = ArrayList<Int>()
    private var scoreboard = Scoreboard()

    private lateinit var throwButton:AppCompatButton

    private lateinit var roundsLeftTV:TextView
    private lateinit var throwsLeftTV:TextView

    private lateinit var die1:ImageButton
    private lateinit var die2:ImageButton
    private lateinit var die3:ImageButton
    private lateinit var die4:ImageButton
    private lateinit var die5:ImageButton
    private lateinit var die6:ImageButton

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

    private lateinit var mapChoiceToInt: Map<AppCompatButton, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Get TextView for rounds and throws left
        roundsLeftTV = findViewById(R.id.nr_of_rounds_left)
        throwsLeftTV = findViewById(R.id.nr_of_throws_left)

        //Set number of rounds and throws left
        roundsLeftTV.text = counter.roundCounter.toString()
        throwsLeftTV.text = counter.throwCounter.toString()

        initializeChoiceButtons()
        initializeSumChoiceButtons()
        initializeDice()
        addDiceToArrayList()

        //Maps the sumChoiceButtons to their corresponding Int
        mapChoiceToInt = mapOf(
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

        val restartButton = findViewById<ImageButton>(R.id.restart_button)
        restartButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        //Get throw button.
        throwButton = findViewById(R.id.throw_btn)
        throwButton.setOnClickListener{
            addThrowButtonFunctionality()
        }

        //Adds functionality to the dice. Toggles between gray and white dice
        for (die in diceList){
            die.setOnClickListener{
                addDieFunctionality(die)
            }
        }

        //Generate the first throw if the session is new
        if(!restoreState(savedInstanceState)){
            throwAllDice()
        }

        for(button in sumChoiceList) {
            //If a choice is already picked (after recreating activity), disable it
            if(chosenOptions.buttonList.contains(mapChoiceToInt[button])){
                button.alpha = .25f
                button.isEnabled = false
            }

            button.setOnClickListener{
                addSumChoiceFunctionality(button)
            }
        }
    }

    private fun initializeChoiceButtons(){
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
    }

    private fun initializeSumChoiceButtons(){
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
    }

    private fun initializeDice(){
        die1 = findViewById(R.id.dice1)
        die2 = findViewById(R.id.dice2)
        die3 = findViewById(R.id.dice3)
        die4 = findViewById(R.id.dice4)
        die5 = findViewById(R.id.dice5)
        die6 = findViewById(R.id.dice6)
    }

    private fun addDiceToArrayList(){
        diceList.add(die1)
        diceList.add(die2)
        diceList.add(die3)
        diceList.add(die4)
        diceList.add(die5)
        diceList.add(die6)
    }

    /**
     * addThrowButtonFunctionality()
     * Method to add functionality to the throw button. Checks if the user has any throws left
     * and if so resets the dice state for the upcoming turn. If the user has no throws left,
     * the throw button will be disabled meaning a choice has to be done.
     */
    private fun addThrowButtonFunctionality(){
        if(counter.hasThrowsLeft()){
            for(die in diceList){
                if(die.isActivated){
                    val newDieVal = dice.throwDie()
                    diceValues.replaceInArray(newDieVal, diceList.indexOf(die))
                    getWhiteDice(die, newDieVal)
                    die.isActivated = false
                }
            }
            counter.decreaseThrows()
            throwsLeftTV.text = counter.throwCounter.toString()
        }

        if(!counter.hasThrowsLeft()){
            for (die in diceList){
                getWhiteDice(die, diceValues.diceValueArray[diceList.indexOf(die)])
            }
            throwButton.isClickable = false
            throwButton.alpha = .5f
        }
    }

    /**
     * addDieFunctionality(ImageView)
     * Function to toggle between activated state and grey/white color
     */
    private fun addDieFunctionality(die: ImageView){
        die.isActivated = !die.isActivated
        if (die.isActivated){
            getGreyDice(die, diceValues.diceValueArray[diceList.indexOf(die)])
        } else {
            getWhiteDice(die, diceValues.diceValueArray[diceList.indexOf(die)])
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

    /**
     * setDiceActivatedFalse()
     * Function to reset the activated state when a turn is over
     */
    private fun setDiceActivatedFalse(){
        for(die in diceList){
            die.isActivated = false
        }
    }

    /**
     * throwAllDice()
     * Throw all dice and give them the new corresponding dice image
     */
    private fun throwAllDice(){
        for(die in diceList){
            val newDieVal = dice.throwDie()
            diceValues.replaceInArray(newDieVal, diceList.indexOf(die))
            getWhiteDice(die, newDieVal)
        }
    }

    /**
     * addSumChoiceFuncionality(AppCompatButton)
     * Adds logic to handle the user's request of picking a sum choice.
     * If the LOW option is chosen, the function assures that all chosen dice are less than
     * or equal 3. Otherwise collect the sum of the dice and verify it is a valid combination.
     */
    private fun addSumChoiceFunctionality(button: AppCompatButton){
        var validDice = true
        for (die in diceList){
            //If LOW is chosen
            if(mapChoiceToInt[button]!! == 3) {
                if(die.isActivated && diceValues.diceValueArray[diceList.indexOf(die)] <= 3) {
                    dice.addToDieSum(diceValues.diceValueArray[diceList.indexOf(die)])
                } else if(die.isActivated && diceValues.diceValueArray[diceList.indexOf(die)] > 3){
                    //A die with value >3 is chosen, invalid for option LOW
                    validDice = false
                }
            }
            //Else get value of chosen dice
            else if(die.isActivated) {
                dice.addToDieSum(diceValues.diceValueArray[diceList.indexOf(die)])
            }
        }

        //A correct sum for the chosen option
        if(dice.checkSumChoice(mapChoiceToInt[button]!!, dice.getDieSum()) && validDice) {
            endTurn(button)
        }

        dice.resetDieSum()

        //Go to ResultsActivity if the game is finished
        if(!counter.hasRoundsLeft()){
            val i = Intent(this, ResultsActivity::class.java)
            var arr = ArrayList<Int>()
            for(score in scoreboard.scores){
                arr.add(score)
            }
            i.putExtra("score", arr)
            i.putExtra("chosenOption", chosenOptions.buttonList)
            startActivity(i)
            finish()
        }
    }

    /**
     * endTurn(AppCompatButton):
     * Calculates and adds the score to the scoreboard. Disables the sumChoice button so it cannot
     * be used again. Reset throw button, reset throws and goes to next round.
     */
    private fun endTurn(button: AppCompatButton){
        scoreboard.addScore(dice.getDieSum())
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
    }

    /**
     * restoreState(Bundle)
     * Retrieves the previously saved progress if the Activity is restored
     */
    private fun restoreState(savedInstanceState: Bundle?) : Boolean {
        if(savedInstanceState != null){
            counter = savedInstanceState.getParcelable(COUNTER_KEY)!!
            diceValues = savedInstanceState.getParcelable(DICE_VALUE_KEY)!!
            chosenOptions = savedInstanceState.getParcelable(CHOSEN_OPTIONS_KEY)!!
            scoreboard = savedInstanceState.getParcelable(SCOREBOARD_KEY)!!

            roundsLeftTV.text = counter.roundCounter.toString()
            throwsLeftTV.text = counter.throwCounter.toString()

            if(!counter.hasThrowsLeft()){
                throwButton.alpha = .25f
                throwButton.isClickable = false
            }

            diceList.forEachIndexed { index, imageButton ->
                getWhiteDice(imageButton, diceValues.diceValueArray[index])
            }
            return true
        }
        return false
    }

    /**
     * onSaveInstanceState(Bundle)
     * Before the Activity resets, save the state and values.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(COUNTER_KEY, counter)
        outState.putParcelable(DICE_VALUE_KEY, diceValues)
        outState.putParcelable(CHOSEN_OPTIONS_KEY, chosenOptions)
        outState.putParcelable(SCOREBOARD_KEY, scoreboard)
    }

    companion object {
        private const val COUNTER_KEY = "GameActivity.Counter"
        private const val DICE_VALUE_KEY = "GameActivity.DiceValue"
        private const val CHOSEN_OPTIONS_KEY = "GameActivity.ChosenOptions"
        private const val SCOREBOARD_KEY = "GameActivity.Scoreboard"
    }
}