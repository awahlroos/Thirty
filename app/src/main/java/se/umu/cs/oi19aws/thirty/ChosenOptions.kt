package se.umu.cs.oi19aws.thirty

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

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