package jono.bedheadalarm;

import java.util.Date;

/**
 * Created by Jono on 27/06/2016.
 */
public class Alarm {

    private String id;
    private String name;
    private Integer hours;
    private Integer mins;
    private Integer vib;

    public Alarm(String id, String name, Integer hours, Integer mins, Integer vib) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.mins = mins;
        this.vib = vib;
    }




    // getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public Integer getMins() {
        return mins;
    }

    public Integer getVib() {
        return vib;
    }
}

