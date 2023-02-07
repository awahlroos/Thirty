package se.umu.cs.oi19aws.thirty.models

import android.os.Parcel
import android.os.Parcelable

/**
 * DiceValue
 * Class responsible for storing the value of the dice currently on the screen
 */
class DiceValue() : Parcelable {

    var diceValueArray = IntArray(6)
    private set

    fun replaceInArray(value:Int, pos:Int){
        diceValueArray[pos] = value
    }

    constructor(parcel: Parcel) : this() {
        diceValueArray = parcel.createIntArray()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeIntArray(diceValueArray)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiceValue> {
        override fun createFromParcel(parcel: Parcel): DiceValue {
            return DiceValue(parcel)
        }

        override fun newArray(size: Int): Array<DiceValue?> {
            return arrayOfNulls(size)
        }
    }
}