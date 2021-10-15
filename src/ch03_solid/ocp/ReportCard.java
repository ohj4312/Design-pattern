package ch03_solid.ocp;

public class ReportCard implements Printable{

    @Override
    public void print() {
        System.out.println("성적표 출력");
    }
}
