package ch03_solid.ocp;


public class StudentDao {
    public void save(){
        System.out.println("데이터베이스에 저장");
    }
    public Student load(){
        System.out.println("데이터베이스 조회");
        return new Student();
    }
}
