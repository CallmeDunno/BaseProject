package com.example.base.data_local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID") val id: Int? = null,
    @ColumnInfo(name = "Name")
    val name: String,
    @ColumnInfo(name = "Age")
    val age: Int
)