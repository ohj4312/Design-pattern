package ch09_observer.practice1_8;

public class BatteryLevelDisplay implements Observer{
    private Battery battery;

    public BatteryLevelDisplay(Battery battery){
        this.battery=battery;
    }

    //변경관리 대상이 되는 데이터, 즉 Battery 클래스의 변경시 호출되는 메서드
    public void update(){
        int level=battery.getLevel();
        System.out.println("Level : "+level); //현재 배터리 잔량을 출력한다.
    }
}
