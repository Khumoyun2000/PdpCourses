package com.example.a10.dars.sodda.pdp.model

import java.io.Serializable

class Mentors : Serializable {
    var id: Int? = null
    var name: String? = null
    var secondName: String? = null
    var middleName: String? = null
    var course: Courses? = null

    constructor()
    constructor(name: String?, secondName: String?, middleName: String?, course: Courses?) {
        this.name = name
        this.secondName = secondName
        this.middleName = middleName
        this.course = course
    }

    constructor(
        id: Int?,
        name: String?,
        secondName: String?,
        middleName: String?,
        course: Courses?
    ) {
        this.id = id
        this.name = name
        this.secondName = secondName
        this.middleName = middleName
        this.course = course
    }


}