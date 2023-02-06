/*package se.umu.cs.oi19aws.thirty

import android.os.Parcel
import android.os.Parcelable

class ThrowsCounter() : Parcelable {

    var counter = 2
        private set

    fun decrease() {
        counter--
    }

    constructor(parcel: Parcel) : this() {
        counter = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(counter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ThrowsCounter> {
        override fun createFromParcel(parcel: Parcel): ThrowsCounter {
            return ThrowsCounter(parcel)
        }

        override fun newArray(size: Int): Array<ThrowsCounter?> {
            return arrayOfNulls(size)
        }
    }
}*/