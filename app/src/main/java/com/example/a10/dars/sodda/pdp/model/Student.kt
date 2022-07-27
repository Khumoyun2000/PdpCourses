package com.example.a10.dars.sodda.pdp.model

import java.io.Serializable

class Student:Serializable {
    var id: Int? = null
    var name: String? = null
    var secondName: String? = null
    var middleName: String? = null
    var date: String? = null
    var courses: Courses? = null
    var groups: Groups? = null
    var mentors: Mentors? = null

    constructor()
    constructor(
        name: String?,
        secondName: String?,
        middleName: String?,
        date: String?,
        courses: Courses?,
        groups: Groups?,
        mentors: Mentors?
    ) {
        this.name = name
        this.secondName = secondName
        this.middleName = middleName
        this.date = date
        this.courses = courses
        this.groups = groups
        this.mentors = mentors
    }

    constructor(
        id: Int?,
        name: String?,
        secondName: String?,
        middleName: String?,
        date: String?,
        courses: Courses?,
        groups: Groups?,
        mentors: Mentors?
    ) {
        this.id = id
        this.name = name
        this.secondName = secondName
        this.middleName = middleName
        this.date = date
        this.courses = courses
        this.groups = groups
        this.mentors = mentors
    }


}