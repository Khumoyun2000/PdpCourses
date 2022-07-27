package com.example.a10.dars.sodda.pdp.model

import java.io.Serializable

class Groups : Serializable {
    var id: Int? = null
    var groupName: String? = null
    var courseTime: String? = null
    var courseDay: String? = null
    var isOpen: String? = null
    var sCount: Int? = null
    var courses: Courses? = null
    var mentor: Mentors? = null

    constructor()
    constructor(
        groupName: String?,
        courseTime: String?,
        courseDay: String?,
        isOpen: String?,
        sCount: Int?,
        courses: Courses?,
        mentor: Mentors?
    ) {
        this.groupName = groupName
        this.courseTime = courseTime
        this.courseDay = courseDay
        this.isOpen = isOpen
        this.sCount=sCount
        this.courses = courses
        this.mentor = mentor
    }

    constructor(
        id: Int?,
        groupName: String?,
        courseTime: String?,
        courseDay: String?,
        isOpen: String?,
        sCount: Int?,
        courses: Courses?,
        mentor: Mentors?

    ) {
        this.id = id
        this.groupName = groupName
        this.courseTime = courseTime
        this.courseDay = courseDay
        this.isOpen = isOpen
        this.sCount=sCount
        this.courses = courses
        this.mentor = mentor
    }


}