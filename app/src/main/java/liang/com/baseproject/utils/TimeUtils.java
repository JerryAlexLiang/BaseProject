package liang.com.baseproject.utils;

/**
 * 创建日期：2019/1/30 on 15:18
 * 描述:
 * 作者: liangyang
 */
public class TimeUtils {

    public static String ms2HMS(int s) {
        String HMStime;
        int hour = s / 3600;
        int mint = (s % 3600) / 60;
        int sed = s % 60;
        String hourStr = String.valueOf(hour);
        if (hour < 10) {
            hourStr = "0" + hourStr;
        }
        String mintStr = String.valueOf(mint);
        if (mint < 10) {
            mintStr = "0" + mintStr;
        }
        String sedStr = String.valueOf(sed);
        if (sed < 10) {
            sedStr = "0" + sedStr;
        }
        HMStime = hourStr + ":" + mintStr + ":" + sedStr;
        return HMStime;
    }

}
