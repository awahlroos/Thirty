package se.umu.cs.oi19aws.thirty

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

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
        //parcel.writeArray(arrayOf(diceValueArray))
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