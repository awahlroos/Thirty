package se.umu.cs.oi19aws.thirty.models

import java.util.Random

/**
 * Dice
 * Class responsible for handling changes of dice values and calculations.
 */
class Dice {

    private var dieSum = 0

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
}