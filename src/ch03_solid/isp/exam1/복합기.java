package ch03_solid.isp.exam1;

public class 복합기 implements 복사기,팩스,프린터{
    public void copy(){
        System.out.println("복사");
    }
    public void fax() {
        System.out.println("팩스");
    }
    public void print () {
        System.out.println("프린터");
    }
}