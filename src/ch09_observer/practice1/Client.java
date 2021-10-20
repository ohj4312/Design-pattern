package ch09_observer.practice1;

public class Client {
    public static void main(String[] args) {
        Battery battery=new Battery();

        BatteryLevelDisplay batteryLevelDisplay=new BatteryLevelDisplay(battery);
        LowBatteryWarning lowBatteryWarning=new LowBatteryWarning(battery);

        battery.setDisplay(batteryLevelDisplay);
        battery.setWraning(lowBatteryWarning);

        battery.consume(20); //100-20
        battery.consume(50); //-50
        battery.consume(10); //-10을 한것
    }
}
