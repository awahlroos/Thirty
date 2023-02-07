package se.umu.cs.oi19aws.thirty.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Counter
 * Class responsible for handling the throws and rounds counter
 */
class Counter() : Parcelable{

    var roundCounter = 10
    private set
    var throwCounter = 2
    private set

    fun decreaseRounds() {
        roundCounter--
    }

    fun decreaseThrows() {
        throwCounter--
    }

    fun resetThrows(){
        throwCounter = 2
    }

    constructor(parcel: Parcel) : this() {
        roundCounter = parcel.readInt()
        throwCounter = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(roundCounter)
        parcel.writeInt(throwCounter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Counter> {
        override fun createFromParcel(parcel: Parcel): Counter {
            return Counter(parcel)
        }

        override fun newArray(size: Int): Array<Counter?> {
            return arrayOfNulls(size)
        }
    }
}