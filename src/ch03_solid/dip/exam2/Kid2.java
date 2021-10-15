package ch03_solid.dip.exam2;

public class Kid2 {
    private Lego toy;//변경

    public void setToy(Lego toy){ //변경
        this.toy=toy;
    }

    public void play(){
        System.out.println(toy.toString());
    }
}
