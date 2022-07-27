package com.example.a10.dars.sodda.pdp.room.dao

import androidx.room.*
import com.example.a10.dars.sodda.pdp.room.entity.*

@Dao
interface PdpDao {
    @Transaction
    @Query("select * from courses")
    fun getGroupOfCourses(): List<CourseAndGroup>

    @Transaction
    @Query("select *from courses")
    fun getMentorsOfCourse(): List<MentorsForCourse>

    @Query("select * from mentors where courseId=:groupId")
    fun getMentorsByCourseId(groupId: Int): List<Mentors>

    @Transaction
    @Query("select * from groups")
    fun getStudentsOfGroup(): List<StudentsAndGroups>

    @Insert
    fun insertCourse(courses: Courses)

    @Query("select * from courses")
    fun getAllCourses(): List<Courses>

    @Query("select * from courses where id=:courseId")
    fun getCourseById(courseId: Int): Courses

    @Insert
    fun insertGroup(groups: Groups)

    @Update
    fun editGroup(groups: Groups)

    @Query("select * from groups where id=:groupId")
    fun getGroupById(groupId: Int): Groups


    @Query("select * from groups")
    fun getAllGroups(): List<Groups>

    @Delete
    fun deleteGroup(groups: Groups)

    @Insert
    fun insertMentor(mentors: Mentors)

    @Query("select * from mentors")
    fun getAllMentors(): List<Mentors>

    @Update
    fun editMentors(mentors: Mentors)

    @Delete
    fun deleteMentor(mentors: Mentors)


    @Insert
    fun insertStudent(student: Student)

    @Update
    fun editStudent(student: Student)

    @Query("select *  from student")
    fun getAllStudents(): List<Student>

    @Query("select * from student where  courseId=:courseId and groupId=:groupId")
    fun getStudentsByGroup(courseId: Int, groupId: Int): List<Student>

    @Delete
    fun deleteStudent(student: Student)

    @Query("DELETE FROM student where courseId=:courseId and groupId=:groupId")
    fun deleteAll(courseId: Int, groupId: Int)

    @Query("delete from student where courseId=:courseId")
    fun deleteStudentsByGroup(courseId: Int)
}