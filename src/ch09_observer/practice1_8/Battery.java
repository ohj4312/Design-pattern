package ch09_observer.practice1_8;

public class Battery extends Subject{ // 변경 관리 대상이 되는 데이터는 Subject를 상속한다.
    private int level=100;


    public void consume(int amount){
        level-=amount;
        notifyObservers(); // 데이터 변경시 Subject 클래스의 notifyObservers() 메소드 호출
    }

    public int getLevel(){
        return level;
    }
}
