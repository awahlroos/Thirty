package se.umu.cs.oi19aws.thirty.models

import android.os.Parcel
import android.os.Parcelable

/**
 * ActiveOptions
 * Class responsible for storing state of dice, whether they are activated or not
 */
class ActiveOptions() : Parcelable{

    var activeArray = IntArray(6)
    private set

    constructor(parcel: Parcel) : this() {
        activeArray = parcel.createIntArray()!!
    }

    fun replaceInArray(value:Int, pos:Int){
        activeArray[pos] = value
    }

    fun resetArray(){
        activeArray = IntArray(6)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeIntArray(activeArray)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ActiveOptions> {
        override fun createFromParcel(parcel: Parcel): ActiveOptions {
            return ActiveOptions(parcel)
        }

        override fun newArray(size: Int): Array<ActiveOptions?> {
            return arrayOfNulls(size)
        }
    }

}