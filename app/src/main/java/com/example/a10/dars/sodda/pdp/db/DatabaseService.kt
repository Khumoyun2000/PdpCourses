package com.example.a10.dars.sodda.pdp.db

import com.example.a10.dars.sodda.pdp.model.Courses
import com.example.a10.dars.sodda.pdp.model.Groups
import com.example.a10.dars.sodda.pdp.model.Mentors
import com.example.a10.dars.sodda.pdp.model.Student

interface DatabaseService {
    fun insertCourse(courses: Courses)
    fun getAllCourses(): ArrayList<Courses>
    fun getCourseById(id: Int): Courses

    fun insertMentor(mentors: Mentors)
    fun editMentor(mentors: Mentors)
    fun deleteMentor(mentors: Mentors)
    fun getAllMentors(): ArrayList<Mentors>
    fun getMentorById(id: Int): Mentors


    fun insertGroup(groups: Groups)
    fun editGroup(groups: Groups)
    fun deleteGroup(groups: Groups)
    fun getAllGroups(): ArrayList<Groups>
    fun getGroupById(id: Int): Groups

    fun insertStudent(student: Student)
    fun editStudent(student: Student)
    fun deleteStudent(student: Student)
    fun getAllStudents(): ArrayList<Student>
    fun deleteAllStudents()

}