package ch03_solid.ocp.exam1;

public class FuelTankMonitoring{
    public void checkAndWanrn(){

        if(checkFuelTank()){
            giveWarningSignal();
        }
    }

    private boolean checkFuelTank(){
        return true;
    }
    private void giveWarningSignal(){
        System.out.println("warning signal");
    }
}