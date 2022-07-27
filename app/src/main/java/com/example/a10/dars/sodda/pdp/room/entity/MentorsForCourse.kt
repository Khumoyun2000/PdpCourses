package com.example.a10.dars.sodda.pdp.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MentorsForCourse(
    @Embedded
    val courses: Courses,

    @Relation(
        parentColumn = "id",
        entityColumn = "courseId"
    )
    val mentors: List<Mentors>
)

