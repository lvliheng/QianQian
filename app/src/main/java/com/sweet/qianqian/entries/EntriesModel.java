package com.sweet.qianqian.entries;

/**
 * Created by lvliheng on 16/12/6.
 */
public class EntriesModel {

    private String month;
    private String day;
    private String week;
    private String weekShort;
    private String time;
    private String position;
    private String weather;
    private String emotion;
    private String title;
    private String content;


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeekShort() {
        return weekShort;
    }

    public void setWeekShort(String weekShort) {
        this.weekShort = weekShort;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EntriesModel{" +
                "month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", week='" + week + '\'' +
                ", weekShort='" + weekShort + '\'' +
                ", time='" + time + '\'' +
                ", position='" + position + '\'' +
                ", weather='" + weather + '\'' +
                ", emotion='" + emotion + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
