package com.example.demo.weatherAdds;

import java.util.Calendar;
import java.util.Date;

public enum WeatherDayPeriodEnum {
    NOT_FOUND(0,0,0),
    NIGHT(0,6,1),
    MORNING(6,12,2),
    AFTERNOON(12,18,3),
    EVENING(18,24,4);

    private int timeFrom;
    private int timeTo;
    private int order;

    private WeatherDayPeriodEnum(int timeFrom, int timeTo, int order) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.order = order;
    }

    public static WeatherDayPeriodEnum getWeatherDayPeriodEnumByDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return getWeatherDayPeriodEnumByDate(cal);
    }

    public static WeatherDayPeriodEnum getWeatherDayPeriodEnumByDate(Calendar cal) {
        WeatherDayPeriodEnum timeRange = WeatherDayPeriodEnum.NOT_FOUND;

        for(WeatherDayPeriodEnum timeRangeEnum:WeatherDayPeriodEnum.values()) {

            if(timeRangeEnum.getTimeFrom() <= cal.get(Calendar.HOUR_OF_DAY) && cal.get(Calendar.HOUR_OF_DAY) < timeRangeEnum.getTimeTo()) {
                timeRange = timeRangeEnum;
                break;
            }
        }

        return timeRange;
    }

    public int getTimeFrom() {
        return timeFrom;
    }
    public void setTimeFrom(int timeFrom) {
        this.timeFrom = timeFrom;
    }
    public int getTimeTo() {
        return timeTo;
    }
    public void setTimeTo(int timeTo) {
        this.timeTo = timeTo;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
}
