package com.example.a10.dars.sodda.pdp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Mentors : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name: String? = null
    var secondName: String? = null
    var middleName: String? = null
    var courseId: Int? = null

    constructor(name: String?, secondName: String?, middleName: String?, courseId: Int?) {
        this.name = name
        this.secondName = secondName
        this.middleName = middleName
        this.courseId = courseId
    }
}



