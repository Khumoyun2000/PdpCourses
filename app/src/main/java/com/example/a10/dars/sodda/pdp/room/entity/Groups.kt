package com.example.a10.dars.sodda.pdp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Groups : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var groupName: String? = null
    var courseTime: String? = null
    var courseDay: String? = null
    var isOpen: String? = null
    var sCount: Int? = null
    var courseId: Int? = null
    var mentorId: Int? = null

    constructor(
        groupName: String?,
        courseTime: String?,
        courseDay: String?,
        isOpen: String?,
        sCount: Int?,
        courseId: Int?,
        mentorId: Int?
    ) {
        this.groupName = groupName
        this.courseTime = courseTime
        this.courseDay = courseDay
        this.isOpen = isOpen
        this.sCount = sCount
        this.courseId = courseId
        this.mentorId = mentorId
    }
}


