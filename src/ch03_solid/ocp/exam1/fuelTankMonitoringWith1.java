package ch03_solid.ocp.exam1;

public class fuelTankMonitoringWith1 extends FuelTankMonitoring1{

    protected boolean checkFuelTank(){
        return true;
    };
    protected void giveWarningSignal(){
        System.out.println("with warning signal");
    };
}
