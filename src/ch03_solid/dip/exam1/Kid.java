package ch03_solid.dip.exam1;

public class Kid{
    private Toy toy;

    public void setToy(Toy toy){ //의존성 주입
        this.toy=toy;
    }

    public void play(){
        System.out.println(toy.toString());
    }
}