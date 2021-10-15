package ch03_solid.dip.exam2;

public class Kid1 {
    private Robot toy;

    public void setToy(Robot toy){
        this.toy=toy;
    }

    public void play(){
        System.out.println(toy.toString());
    }
}
