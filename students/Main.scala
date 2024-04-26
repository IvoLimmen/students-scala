package students

import scala.io.StdIn.readLine

class Course(name: String):

  var grades: Array[Float] = Array()

  def getName() = this.name

  def addGrade(grade: Float) = {
    grades = grades :+ grade
  }

  def totals() = {
    val count = this.grades.length
    if (count == 0) {
      println("No grades")
    } else {
      val total = this.grades.reduce((p, c) => p + c)
      val avg = total / count
      printf("%s - %d exams - %3.2f avg\n", name, count, avg)
    }
  }

class Student(name: String):

  var courses: Array[Course] = Array()

  def getName() = this.name

  def addGrade(course: String, grade: Float) = {
    var c = courses.find(c => c.getName() == course)
    
    if (c.isEmpty) {
      c = Option(Course(course))
      courses = courses :+ c.get
    }  
    c.get.addGrade(grade)
  }

  def totals() = {
    println(s"Grades for $name")
    this.courses.foreach(c => c.totals())
  }

def help() = {
  println("Grading system");
  println("add-student [name] - Add student");
  println("select-student [name] - Select a student");
  println("add-grade [course] [grade] - Add a grade for a course for the current selected student");
  println("end - Stop the program");
}

@main
def main() = {

  var students: Array[Student] = Array()
  var student: Student = null

  var stop = false
  while !stop do
    print("Input command:")
    val command = readLine()

    if (command == "help") {
      help()
    }

    if (command == "end" || command == "quit") {
      stop = true
    }

    if (command.startsWith("add-student")) {
      val name = command.substring(11).trim()
      student = Student(name)
      students = students :+ student
    } 

    if (command.startsWith("add-grade")) {
      var arg = command.substring(9).trim();
      var args = arg.split(" ");

      if (student != null) {
        student.addGrade(args(0), args(1).toFloat);
      } else {
        println("No student is selected");
      }
    }

    if (command.startsWith("select-student")) {
      var name = command.substring(14).trim();
      student = students.find(s => s.getName() == name).getOrElse(null);
      if (student != null) {
        printf("Student %s is selected\n", name);
      } else {
        printf("Student %s not found\n", name);
      }
    }

  students.foreach(s => s.totals())
}
