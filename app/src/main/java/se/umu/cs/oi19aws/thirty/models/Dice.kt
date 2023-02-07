package se.umu.cs.oi19aws.thirty.models

import android.widget.ImageButton
import java.util.Random

/**
 * Dice
 * Class responsible for handling changes of dice values. Also holds the other model classes as
 * they are dependent of this class in order to work as intended.
 */
class Dice {

    private var dieSum = 0
    var counter = Counter()
    var chosenOptions = ChosenOptions()
    var diceValues = DiceValue()
    var chosenOption = ArrayList<Int>()
    var scoreboard = Scoreboard()

    fun getDieSum():Int {
        return dieSum
    }

    fun addToDieSum(value:Int){
        dieSum += value
    }

    fun resetDieSum(){
        dieSum = 0
    }

    //Here you could check if a user has diceSum 0, but in case the user
    //wants to skip the round the user can use the 0 points on any sum,
    //hence 0 modulo x = 0.
    fun checkSumChoice(choice:Int, diceSum:Int): Boolean {
        //All dice values are less than 3, valid choice
        if(choice == 3) {
            return true
        }
        return diceSum % choice == 0
    }

    fun throwDie():Int {
        //Random in Kotlin generated the same sequence, therefore Java's Random is used
        return Random().nextInt(6)+1
    }

    fun hasRoundsLeft():Boolean {
        return counter.roundCounter > 0
    }

    fun hasThrowsLeft():Boolean {
        return counter.throwCounter > 0
    }

    fun updateDieValue(diceList: List<ImageButton>, die:ImageButton, newDieVal:Int){
        diceValues.replaceInArray(newDieVal, diceList.indexOf(die))
    }
}