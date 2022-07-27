package com.example.a10.dars.sodda.pdp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Student{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name: String? = null

    constructor(
        name: String?,
        secondName: String?,
        middleName: String?,
        date: String?,
        courseId: Int?,
        groupId: Int?,
        mentorId: Int?
    ) {
        this.name = name
        this.secondName = secondName
        this.middleName = middleName
        this.date = date
        this.courseId = courseId
        this.groupId = groupId
        this.mentorId = mentorId
    }

    var secondName: String? = null
    var middleName: String? = null
    var date: String? = null
    var courseId: Int? = null
    var groupId: Int? = null
    var mentorId: Int? = null

}







