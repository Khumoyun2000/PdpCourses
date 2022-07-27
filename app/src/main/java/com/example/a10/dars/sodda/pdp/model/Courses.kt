package com.example.a10.dars.sodda.pdp.model

import java.io.Serializable

class Courses:Serializable {
    var id: Int? = null
    var courseName: String? = null
    var courseDescription: String? = null

    constructor()
    constructor(courseName: String?, courseDescription: String?) {
        this.courseName = courseName
        this.courseDescription = courseDescription
    }

    constructor(id: Int?, courseName: String?, courseDescription: String?) {
        this.id = id
        this.courseName = courseName
        this.courseDescription = courseDescription
    }

    constructor(id: Int?) {
        this.id = id
    }

}