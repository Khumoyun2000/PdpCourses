package com.example.a10.dars.sodda.pdp

object Constants {
    const val DB_NAME = "course_db"
    const val DB_VERSION = 1

    const val COURSES_TABLE = "courses"
    const val ID = "id"
    const val COURSE_NAME = "name"
    const val COURSE_DESCRIPTION = "description"


    const val MENTORS_TABLE = "mentors"
    const val MENTOR_ID = "id"
    const val MENTOR_NAME = "name"
    const val MENTOR_SURNAME = "surname"
    const val MENTOR_MIDDLE_NAME = "middle_name"
    const val MENTORS_COURSE = "mentors_course"

    const val GROUPS_TABLE = "groups"
    const val GROUPS_ID = "id"
    const val GROUP_NAME = "name"
    const val TIME = "time"
    const val DAY = "day"
    const val GROUP_IS_OPENED = "open"
    const val STUDENTS_COUNT = "count"
    const val GROUP_COURSE = "group_course"
    const val GROUP_MENTORS = "mentors_group"

    const val STUDENTS_TABLE = "students"
    const val STUDENT_ID = "id"
    const val STUDENT_NAME = "name"
    const val STUDENT_SECOND_NAME = "surname"
    const val STUDENT_MIDDLE_NAME = "middle_name"
    const val STUDENT_ADDED_DATE = "added_date"
    const val STUDENT_COURSES = "student_course"
    const val STUDENT_GROUP = "student_group"
    const val STUDENT_MENTOR = "student_mentor"

}