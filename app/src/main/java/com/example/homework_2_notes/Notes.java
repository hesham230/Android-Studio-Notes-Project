package com.example.homework_2_notes;

public class Notes {

    /*
    simple class than allows us to create
    Objects from the type Notes.
    each object from this type must have
    a specific time, description of the note and picture

     */



    // Attributes



    private int id;
    private String desc;
    private String time;
    private int pic;


    // Get picture
    public int getPic() {
        return pic;
    }

    // Set Picture
    public void setPic(int pic) {
        this.pic = pic;
    }

    // Constructor

    public Notes(){

    }


    public Notes(String desc, String time, int pic){

        this.desc = desc;
        this.time = time;
        this.pic = pic;
    }

    // get time
    public String getTime() {
        return time;
    }

    // set time
    public void setTime(String time) {
        this.time = time;
    }

    // get description
    public String getDesc() {
        return desc;
    }

    // set description
    public void setDesc(String desc) {
        this.desc = desc;
    }

    // get id
    public int getId() {
        return id;
    }

    // set id
    public void setId(int id) {
        this.id = id;
    }
}
