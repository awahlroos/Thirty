package se.umu.cs.oi19aws.thirty.models

import android.os.Parcel
import android.os.Parcelable

/**
 * ChosenOptions
 * Class responsible for storing which options (LOW,3,4,..,12) that are already chosen, as they
 * can only be chosen once per game
 */
class ChosenOptions() : Parcelable {

    var buttonList = ArrayList<Int>()
    private set

    fun addChoice(choice: Int){
        buttonList.add(choice)
    }

    constructor(parcel: Parcel) : this() {
        val size = parcel.readInt()
        buttonList = ArrayList(size)
        for (i in 0 until size) {
            buttonList.add(parcel.readInt())
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(buttonList.size)
        for (i in buttonList) {
            parcel.writeInt(i)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChosenOptions> {
        override fun createFromParcel(parcel: Parcel): ChosenOptions {
            return ChosenOptions(parcel)
        }

        override fun newArray(size: Int): Array<ChosenOptions?> {
            return arrayOfNulls(size)
        }
    }
}