package ch03_solid.ocp;

public class AttBook implements Printable{

    @Override
    public void print() {
        System.out.println("출석부 출력");
    }
}
