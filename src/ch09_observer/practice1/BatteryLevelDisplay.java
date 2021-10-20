package ch09_observer.practice1;

//배터리 양을 관리하는 클래스2
//배터리 잔량이 변경될때 마다 배터리 잔량을 출력한다.
public class BatteryLevelDisplay {
    private Battery battery;

    public BatteryLevelDisplay(Battery battery){
        this.battery=battery;
    }

    public void update(){
        int level=battery.getLevel();
        System.out.println("Level : "+level);
    }

}
