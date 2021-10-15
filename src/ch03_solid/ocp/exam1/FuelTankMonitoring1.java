package ch03_solid.ocp.exam1;

public class FuelTankMonitoring1 {
    public void checkAndWanrn(){

        if(checkFuelTank()){
            giveWarningSignal();
        }
    }

    protected boolean checkFuelTank(){
        return true;
    };
    protected void giveWarningSignal(){
        System.out.println("warning signal");
    };
}



