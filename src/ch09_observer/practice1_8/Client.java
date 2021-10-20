package ch09_observer.practice1_8;

public class Client {
    public static void main(String[] args) {
        Battery battery=new Battery();

        Observer batteryDisplay=new BatteryLevelDisplay(battery); //BatteryLevelDisplay 옵서버 생성
        Observer lowBatteryWarning=new LowBatteryWarning(battery); //LowBatteryWarning 옵서버 생성

        //BatteryLevelDisplay 옵서버를 Battery(Subject)에 설정
        battery.attach(batteryDisplay);
        //LowBatteryWarning 옵서버를 Battery(Subject)에 설정
        battery.attach(lowBatteryWarning);

        battery.consume(20); //100-20
        battery.consume(50); //-50
        battery.consume(10); //-10을 한것
    }
}
