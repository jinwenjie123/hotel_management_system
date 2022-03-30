package ca.ubc.cs304.model;
import java.util.Date;

public class Room {
    private int room_number;
    private int price;
    private String kind;
    private String state;
    private int hotel_id;

    public Room(int room_number, int price, String kind, String state, int hotel_id){
        this.room_number = room_number;
        this.price = price;
        this.kind = kind;
        this.state = state;
        this.hotel_id = hotel_id;
    }

    public int getRoom_number(){return room_number;}
    public void setRoom_number(int room_number){this.room_number = room_number;}

    public int getPrice(){return price;}

    public void setPrice(int price) {this.price = price;}

    public String getKind() {return kind;}

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }
}
