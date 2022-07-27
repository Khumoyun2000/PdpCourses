package com.example.a10.dars.sodda.pdp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.a10.dars.sodda.pdp.Constants
import com.example.a10.dars.sodda.pdp.model.Courses
import com.example.a10.dars.sodda.pdp.model.Groups
import com.example.a10.dars.sodda.pdp.model.Mentors
import com.example.a10.dars.sodda.pdp.model.Student

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val coursesQuery =
            "CREATE TABLE    ${Constants.COURSES_TABLE} ( ${Constants.ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE , ${Constants.COURSE_NAME} TEXT NOT NULL , ${Constants.COURSE_DESCRIPTION} TEXT NOT NULL )"
        val mentorsQuery =
            "CREATE TABLE ${Constants.MENTORS_TABLE} ( ${Constants.MENTOR_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constants.MENTOR_NAME} TEXT NOT NULL, ${Constants.MENTOR_SURNAME} TEXT NOT NULL, ${Constants.MENTOR_MIDDLE_NAME} TEXT NOT NULL, ${Constants.MENTORS_COURSE} INTEGER NOT NULL, FOREIGN KEY (${Constants.MENTORS_COURSE}) REFERENCES ${Constants.COURSES_TABLE} (${Constants.ID}))"
        val groupsQuery =
            "CREATE TABLE ${Constants.GROUPS_TABLE} ( ${Constants.GROUPS_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constants.GROUP_NAME} TEXT NOT NULL, ${Constants.TIME} TEXT NOT NULL, ${Constants.DAY} TEXT NOT NULL, ${Constants.GROUP_IS_OPENED} text not null, ${Constants.STUDENTS_COUNT} INTEGER NOT NULL, ${Constants.GROUP_COURSE} INTEGER NOT NULL, ${Constants.GROUP_MENTORS} INTEGER NOT NULL, FOREIGN KEY (${Constants.GROUP_COURSE}) REFERENCES ${Constants.COURSES_TABLE} (${Constants.ID}), FOREIGN KEY (${Constants.GROUP_MENTORS}) REFERENCES ${Constants.MENTORS_TABLE} (${Constants.MENTOR_ID}))"
        val studentQuery =
            "CREATE TABLE ${Constants.STUDENTS_TABLE} ( ${Constants.STUDENT_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constants.STUDENT_NAME} TEXT NOT NULL, ${Constants.STUDENT_SECOND_NAME} TEXT NOT NULL, ${Constants.STUDENT_MIDDLE_NAME} TEXT NOT NULL, ${Constants.STUDENT_ADDED_DATE} TEXT NOT NULL, ${Constants.STUDENT_COURSES} INTEGER NOT NULL, ${Constants.STUDENT_GROUP} INTEGER NOT NULL, ${Constants.STUDENT_MENTOR} INTEGER NOT NULL, FOREIGN KEY (${Constants.STUDENT_COURSES}) REFERENCES ${Constants.COURSES_TABLE} (${Constants.ID}), FOREIGN KEY (${Constants.STUDENT_GROUP}) REFERENCES ${Constants.GROUPS_TABLE} (${Constants.GROUPS_ID}), FOREIGN KEY (${Constants.STUDENT_MENTOR}) REFERENCES ${Constants.MENTORS_TABLE} (${Constants.MENTOR_ID}))"

        db?.execSQL(coursesQuery)
        db?.execSQL(mentorsQuery)
        db?.execSQL(groupsQuery)
        db?.execSQL(studentQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun insertCourse(courses: Courses) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.COURSE_NAME, courses.courseName)
        contentValues.put(Constants.COURSE_DESCRIPTION, courses.courseDescription)
        database.insert(Constants.COURSES_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllCourses(): ArrayList<Courses> {
        val coursesList = ArrayList<Courses>()
        val query = "select * from ${Constants.COURSES_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val courses = Courses(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                coursesList.add(courses)
            } while (cursor.moveToNext())
        }
        return coursesList
    }

    override fun getCourseById(id: Int): Courses {
        val database = this.readableDatabase
        val cursor = database.query(
            Constants.COURSES_TABLE,
            arrayOf(Constants.ID, Constants.COURSE_NAME, Constants.COURSE_DESCRIPTION),
            "${Constants.ID} = ?",
            arrayOf("${id}"),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        return Courses(cursor.getInt(0), cursor.getString(1), cursor.getString(2))

    }

    override fun insertMentor(mentors: Mentors) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.MENTOR_NAME, mentors.name)
        contentValues.put(Constants.MENTOR_SURNAME, mentors.secondName)
        contentValues.put(Constants.MENTOR_MIDDLE_NAME, mentors.middleName)
        contentValues.put(Constants.MENTORS_COURSE, mentors.course?.id)
        database.insert(Constants.MENTORS_TABLE, null, contentValues)
        database.close()
    }

    override fun editMentor(mentors: Mentors) {
        val database = this.writableDatabase
        val values = ContentValues().apply {
            put(Constants.MENTOR_ID, mentors.id)
            put(Constants.MENTOR_NAME, mentors.name)
            put(Constants.MENTOR_SURNAME, mentors.secondName)
            put(Constants.MENTOR_MIDDLE_NAME, mentors.middleName)
            put(Constants.MENTORS_COURSE, mentors.course?.id)
        }
        database.update(
            Constants.MENTORS_TABLE,
            values,
            "${Constants.MENTOR_ID} = ?",
            arrayOf("${mentors.id}")
        )
        database.close()
    }

    override fun deleteMentor(mentors: Mentors) {
        val database = this.writableDatabase
        database.delete(
            Constants.MENTORS_TABLE,
            "${Constants.MENTOR_ID} = ?",
            arrayOf(mentors.id.toString())
        )
        database.close()
    }

    override fun getAllMentors(): ArrayList<Mentors> {
        val mentorsList = ArrayList<Mentors>()
        val query = "select * from ${Constants.MENTORS_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentor = Mentors(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    getCourseById(cursor.getInt(4))
                )
                mentorsList.add(mentor)
            } while (cursor.moveToNext())
        }
        return mentorsList

    }

    override fun getMentorById(id: Int): Mentors {
        val database = this.readableDatabase
        val cursor = database.query(
            Constants.MENTORS_TABLE,
            arrayOf(
                Constants.MENTOR_ID,
                Constants.MENTOR_NAME,
                Constants.MENTOR_SURNAME,
                Constants.MENTOR_MIDDLE_NAME,
                Constants.MENTORS_COURSE
            ),
            "${Constants.ID} = ?",
            arrayOf("${id}"),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        return Mentors(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            getCourseById(cursor.getInt(4))
        )
    }

    override fun insertGroup(groups: Groups) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.GROUP_NAME, groups.groupName)
        contentValues.put(Constants.TIME, groups.courseTime)
        contentValues.put(Constants.DAY, groups.courseDay)
        contentValues.put(Constants.GROUP_IS_OPENED, groups.isOpen)
        contentValues.put(Constants.STUDENTS_COUNT, groups.sCount)
        contentValues.put(Constants.GROUP_COURSE, groups.courses?.id)
        contentValues.put(Constants.GROUP_MENTORS, groups.mentor?.id)
        database.insert(Constants.GROUPS_TABLE, null, contentValues)
        database.close()
    }

    override fun editGroup(groups: Groups) {
        val database = this.writableDatabase
        val values = ContentValues().apply {
            put(Constants.GROUPS_ID, groups.id)
            put(Constants.GROUP_NAME, groups.groupName)
            put(Constants.TIME, groups.courseTime)
            put(Constants.DAY, groups.courseDay)
            put(Constants.GROUP_IS_OPENED, groups.isOpen)
            put(Constants.STUDENTS_COUNT, groups.sCount)
            put(Constants.GROUP_COURSE, groups.courses?.id)
            put(Constants.GROUP_MENTORS, groups.mentor?.id)
        }
        database.update(
            Constants.GROUPS_TABLE,
            values,
            "${Constants.GROUPS_ID} = ?",
            arrayOf("${groups.id}")
        )
        database.close()
    }

    override fun deleteGroup(groups: Groups) {
        val database = this.writableDatabase
        database.delete(
            Constants.GROUPS_TABLE,
            "${Constants.GROUPS_ID} = ?",
            arrayOf(groups.id.toString())
        )
        database.close()
    }

    override fun getAllGroups(): ArrayList<Groups> {
        val groupsList = ArrayList<Groups>()
        val query = "select * from ${Constants.GROUPS_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val groups = Groups(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    getCourseById(cursor.getInt(6)),
                    getMentorById(cursor.getInt(7))
                )
                groupsList.add(groups)
            } while (cursor.moveToNext())
        }
        return groupsList

    }

    override fun getGroupById(id: Int): Groups {
        val database = this.readableDatabase
        val cursor = database.query(
            Constants.GROUPS_TABLE,
            arrayOf(
                Constants.GROUPS_ID,
                Constants.GROUP_NAME,
                Constants.TIME,
                Constants.DAY,
                Constants.GROUP_IS_OPENED,
                Constants.STUDENTS_COUNT,
                Constants.GROUP_COURSE,
                Constants.GROUP_MENTORS
            ),
            "${Constants.GROUPS_ID} = ?",
            arrayOf("${id}"),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        return Groups(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getInt(5),
            getCourseById(cursor.getInt(6)),
            getMentorById(cursor.getInt(7))
        )
    }

    override fun insertStudent(student: Student) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.STUDENT_NAME, student.name)
        contentValues.put(Constants.STUDENT_SECOND_NAME, student.secondName)
        contentValues.put(Constants.STUDENT_MIDDLE_NAME, student.middleName)
        contentValues.put(Constants.STUDENT_ADDED_DATE, student.date)
        contentValues.put(Constants.STUDENT_COURSES, student.courses?.id)
        contentValues.put(Constants.STUDENT_GROUP, student.groups?.id)
        contentValues.put(Constants.STUDENT_MENTOR, student.mentors?.id)
        database.insert(Constants.STUDENTS_TABLE, null, contentValues)
        database.close()
    }

    override fun editStudent(student: Student) {
        val database = this.writableDatabase
        val values = ContentValues().apply {
            put(Constants.GROUPS_ID, student.id)
            put(Constants.STUDENT_NAME, student.name)
            put(Constants.STUDENT_SECOND_NAME, student.secondName)
            put(Constants.STUDENT_MIDDLE_NAME, student.middleName)
            put(Constants.STUDENT_ADDED_DATE, student.date)
            put(Constants.STUDENT_COURSES, student.courses?.id)
            put(Constants.STUDENT_GROUP, student.groups?.id)
            put(Constants.STUDENT_MENTOR, student.mentors?.id)
        }
        database.update(
            Constants.STUDENTS_TABLE,
            values,
            "${Constants.STUDENT_ID} = ?",
            arrayOf("${student.id}")
        )
        database.close()
    }

    override fun deleteStudent(student: Student) {
        val database = this.writableDatabase
        database.delete(
            Constants.STUDENTS_TABLE,
            "${Constants.STUDENT_ID} = ?",
            arrayOf(student.id.toString())
        )
        database.close()
    }

    override fun getAllStudents(): ArrayList<Student> {
        val studentList = ArrayList<Student>()
        val query = "select * from ${Constants.STUDENTS_TABLE}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val student = Student(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        getCourseById(cursor.getInt(5)),
                        getGroupById(cursor.getInt(6)),
                        getMentorById(cursor.getInt(7))
                    )
                    studentList.add(student)
                } while (cursor.moveToNext())
            }
        }
        return studentList
    }

    override fun deleteAllStudents() {
        val database = this.writableDatabase
        database.delete(Constants.STUDENTS_TABLE, null, null)
        database.close()
    }


}