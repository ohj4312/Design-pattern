package ch03_solid.ocp.exam2;

import java.util.Calendar;

public class TimeReminder{
    private MP3 m;

    public void reminder(){
        Calendar cal=Calendar.getInstance();
        m=new MP3();
        int hour=cal.get(Calendar.HOUR_OF_DAY);

        if(hour>=22){
            m.playSong();
        }
    }
}