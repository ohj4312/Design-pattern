package ch03_solid.ocp;

import ch03_solid.srp.exam1.Course;

public class Student {
    public void getCourses(){
        System.out.println("수강과목 조회");
    }
    public void addCourse(Course C){
        System.out.println("수강과목 추가");
    }
}
