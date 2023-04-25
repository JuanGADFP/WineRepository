package org.juang.test.springboot.app.models;


import javax.persistence.*;

@Entity
@Table(name = "wines")
public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String name;
    private String winery;
    private int año;

    public Wine() {
    }

    public Wine(Long ID, String name, String winery, int año) {
        this.ID = ID;
        this.name = name;
        this.winery = winery;
        this.año = año;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWinery() {
        return winery;
    }

    public void setWinery(String winery) {
        this.winery = winery;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }
}
