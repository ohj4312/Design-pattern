package ch03_solid.srp.exam1;

public class Student {
    public void getCourses(){
        System.out.println("수강과목 조회");
    }
    public void addCourse(Course C){
        System.out.println("수강과목 추가");
    }

    public void save(){
        System.out.println("데이터베이스에 저장");
    }
    public Student load(){
        System.out.println("데이터베이스 조회");
        return new Student();
    }
    public void printOnReportCard(){
        System.out.println("성적표 출력");
    }
    public void printOnAttendanceBook(){
        System.out.println("출석부 출력");
    }
}
