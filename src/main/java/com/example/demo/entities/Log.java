package com.example.demo.entities;

import javax.persistence.*;



@Entity
@Table(name = "log")
public class Log{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "log_type")
    private String logType;

    @Column(name = "at_time")
    private String atTime;

    @Column(name = "target_id")
    private int targetId;

    @Column(name = "description")
    private String description;

    public Log() {

    }
    public Log(String logType, String atTime, int targetId, String description) {
        this.logType = logType;
        this.atTime = atTime;
        this.targetId = targetId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
