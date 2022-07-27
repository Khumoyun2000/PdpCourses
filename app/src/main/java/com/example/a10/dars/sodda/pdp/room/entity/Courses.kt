package com.example.a10.dars.sodda.pdp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
 class Courses:Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null
    var courseName:String?=null
    var courseDescription:String?=null

    constructor(courseName: String?, courseDescription: String?) {
        this.courseName = courseName
        this.courseDescription = courseDescription
    }
}


