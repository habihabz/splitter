package com.example.roomsplitter;

import java.util.List;

public class Room {
    public String room_id;
    public String room_name;

    public Room() {
    }

    public Room(String room_id, String room_name) {
        this.room_id = room_id;
        this.room_name = room_name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

}
