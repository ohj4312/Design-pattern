package ch03_solid.dip.exam2;

public class ToyTest2 {
    public static void main(String[] args){
        Lego t=new Lego(); // 변경
        Kid2 k=new Kid2();
        k.setToy(t);
        k.play();
    }
}
