package com.example.databaseproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo (
    //Entity
    var title:String,
    var createdAt:String

){
    @PrimaryKey(autoGenerate = true)
    var id =0
}