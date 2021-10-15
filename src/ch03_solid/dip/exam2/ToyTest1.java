package ch03_solid.dip.exam2;

public class ToyTest1 {
    public static void main(String[] args){
        Robot t=new Robot();
        Kid1 k=new Kid1();
        k.setToy(t);
        k.play();
    }
}
