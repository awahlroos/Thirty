package se.umu.cs.oi19aws.thirty.models

import java.util.Random

/**
 * Dice
 * Class responsible for handling changes of dice values and calculations.
 */
class Dice {

    private var dieSum = ArrayList<Int>()
    private var nrOfCombinations = 0

    fun addToDieSum(value:Int){
        dieSum.add(value)
    }

    fun resetDieSum(){
        dieSum = ArrayList()
        nrOfCombinations = 0
    }

    //Here you could check if a user has diceSum 0, but in case the user
    //wants to skip the round the user can use the 0 points on any sum,
    //hence 0 modulo x = 0.
    fun checkSumChoice(choice:Int): Boolean {
        //All dice values are less than 3, valid choice
        if(choice == 3) {
            return true
        }

        //Skip individual checks if the total sum is invalid
        if(dieSum.sum() % choice != 0){
            return false
        }

        val combinations = dieSum.combinations()

        //The combinations are not unique, meaning multiple dice can be paired differently in
        //multiple combinations, however, the result will still be the same as it is a valid
        //pairing/sum of the dice. Variable nrOfCombinations is only used to verify the least
        //number of combinations given the choice and the sum of the dice.
        combinations.forEach {
            if(it.sum() == choice){
                nrOfCombinations++
            }
        }
        return(choice * nrOfCombinations >= dieSum.sum() || dieSum.sum() == 0)
    }

    /**
     * combinations()
     * A higher-order recursive function to calculate all possible combinations of the chosen dice.
     */
    private fun <T> List<T>.combinations(): List<List<T>> {
        if (isEmpty()) {
            return listOf(emptyList())
        }

        val firstElem = first()
        val remaining = drop(1)

        //Recursively calculate sub combinations based on the remaining dice values
        val subCombinations = remaining.combinations()
        return subCombinations + subCombinations.map { it + firstElem }
    }

    fun getPoints():Int {
        return dieSum.sum()
    }

    fun throwDie():Int {
        //Random in Kotlin generated the same sequence, therefore Java's Random is used
        return Random().nextInt(6)+1
    }
}