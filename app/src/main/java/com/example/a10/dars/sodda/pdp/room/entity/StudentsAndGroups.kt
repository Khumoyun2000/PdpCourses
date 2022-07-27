package com.example.a10.dars.sodda.pdp.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class StudentsAndGroups(
    @Embedded
    val groups: Groups,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val students: List<Student>
)

