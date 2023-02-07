package se.umu.cs.oi19aws.thirty.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Scoreboard
 * Class responsible for saving scores for each round
 */
class Scoreboard() : Parcelable{

    var scores = ArrayList<Int>()
    private set

    fun addScore(score:Int){
        scores.add(score)
    }

    constructor(parcel: Parcel) : this() {
        val size = parcel.readInt()
        scores = ArrayList(size)
        for(i in 0 until size){
            scores.add(parcel.readInt())
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(scores.size)
        for (i in scores) {
            parcel.writeInt(i)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Scoreboard> {
        override fun createFromParcel(parcel: Parcel): Scoreboard {
            return Scoreboard(parcel)
        }

        override fun newArray(size: Int): Array<Scoreboard?> {
            return arrayOfNulls(size)
        }
    }
}