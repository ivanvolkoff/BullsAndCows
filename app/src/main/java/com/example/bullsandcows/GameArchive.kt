package com.example.bullsandcows

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class GameArchive(var gameList:ArrayList<GameModel> ): Serializable{

}