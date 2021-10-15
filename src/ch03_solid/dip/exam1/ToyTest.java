package ch03_solid.dip.exam1;

public class ToyTest {
    public static void main(String[] args){
        //로봇 장난감을 가지고 노는 경우
        Toy t=new Robot();
        Kid k1=new Kid();
        k1.setToy(t);
        k1.play();

        //레고 장난감을 가지고 노는 경우
        Toy l=new Lego();
        Kid k2=new Kid();
        k2.setToy(l);
        k2.play();

    }
}
