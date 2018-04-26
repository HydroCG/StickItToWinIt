package me.callum.ld41.utils;

public class TimeUtils {

    public static String formatTimeMMSS(int seconds) {
        int minutes = (int)Math.floor(seconds/60);
        seconds = seconds - (minutes * 60);

        String str = "";
        if(minutes<10) {
            str = "0"+minutes;
        } else {
            str = ""+minutes;
        }

        if(seconds<10) {
            str += ":0"+seconds;
        } else {
            str += ":"+seconds;
        }
        return str;
    }
}
