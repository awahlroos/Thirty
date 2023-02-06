package se.umu.cs.oi19aws.thirty

import android.os.Parcel
import android.os.Parcelable

class DiceValue() : Parcelable {

    var diceValueArray = ArrayList<Int>()
    private set

    fun addToArray(value:Int){
        diceValueArray.add(value)
    }

    fun replaceInArray(value:Int, pos:Int){
        diceValueArray[pos] = value
    }

    constructor(parcel: Parcel) : this() {
        diceValueArray = parcel.readArrayList(Int::class.java.classLoader) as ArrayList<Int>
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeArray(arrayOf(diceValueArray))
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