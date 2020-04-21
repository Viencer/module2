package com.yuriy.entity;

public class Question {
    int id;
    String qest;
    String answ;
    int points;

    public Question(int id, String qest, String answ, int points) {
        this.id = id;
        this.qest = qest;
        this.answ = answ;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQest() {
        return qest;
    }

    public void setQest(String qest) {
        this.qest = qest;
    }

    public String getAnsw() {
        return answ;
    }

    public void setAnsw(String answ) {
        this.answ = answ;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", qest='" + qest + '\'' +
                ", answ='" + answ + '\'' +
                ", points=" + points +
                '}';
    }
}
