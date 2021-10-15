package ch03_solid.ocp.exam2.sol;

import ch03_solid.ocp.exam2.MP3;

import java.util.Calendar;

public class TimeReminder{
    TimeProvider tProv;
    MP3 m = new MP3();

    public void setTimeProvider(TimeProvider tProv){
        this.tProv = tProv; //테스트 스텁이나 실제 시간을 제공하는 인스턴스를 주입
    }
    public void reminder(){
        int hour=tProv.getTime();
        if(hour>=22){
            m.playSong();
        }
    }
}