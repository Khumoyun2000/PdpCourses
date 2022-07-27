package com.example.a10.dars.sodda.pdp.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CourseAndGroup(
    @Embedded
    val courses: Courses,
    @Relation(
        parentColumn = "id",
        entityColumn = "courseId"
    )
    val groups: List<Groups>
)
