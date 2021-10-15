package ch03_solid.ocp;

public class SomeClient implements Printable{

    @Override
    public void print() {
        System.out.println("도서관 대여 명부 출력");
    }
}
