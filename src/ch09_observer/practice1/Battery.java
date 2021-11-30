package ch09_observer.practice1;

//배터리 양을 관리하는 클래스1
//배터리 잔량에 변경이 생기면 BatteryLevellDisplay 클래스와 LowBatteryWarning 클래스에게 배터리 잔량이 변경되었음을 알린다.
public class Battery {
    private int level=100;
    private BatteryLevelDisplay display; //OCP 위반
    private LowBatteryWarning wraning; //OCP 위반

    public void setDisplay(BatteryLevelDisplay display){
        this.display=display;
    } //OCP위반

    public void setWraning(LowBatteryWarning wraning){
        this.wraning=wraning;
    } //OCP 위반

    public void consume(int amount){
        level-=amount;
        display.update(); //OCP 위반
        wraning.update(); //OCP 위반
    }

    public int getLevel(){
        return level;
    }
}
