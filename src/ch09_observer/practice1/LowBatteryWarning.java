package ch09_observer.practice1;

//배터리 양을 관리하는 클래스3
//잔량이 30보다 작으면 배터리 부족 경고를 출력한다.
public class LowBatteryWarning {
    private static final int LOW_BATTERY=30;
    private Battery battery;

    public LowBatteryWarning(Battery battery){
        this.battery=battery;
    }

    public void update(){
        int level=battery.getLevel();
        if (level<LOW_BATTERY){
            System.out.println("<Warning> Low Battery : "+level+" Compared with "+LOW_BATTERY);
        }
    }
}
