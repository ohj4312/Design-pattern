package ch03_solid.ocp.exam2.sol;

import java.util.Calendar;

public class FakeTimeProvider implements TimeProvider{ // TimProvider 테스트 스텁
    private Calendar cal;

    public FakeTimeProvider(){
        cal=Calendar.getInstance();
    }

    public FakeTimeProvider(int hours){
        cal=Calendar.getInstance();
        setHours(hours);
    }

    public void setHours(int hours){
        cal.set(Calendar.HOUR_OF_DAY,hours); // 주어진 시간으로 시간 설정
    }

    public int getTime(){
        return cal.get(Calendar.HOUR_OF_DAY); // 현재 시간 반환
    }
}