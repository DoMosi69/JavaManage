package sample;

import java.time.LocalDate;

public class Customerdb {

    private int id;
    private String name;
    private String sex;
    private String tel;
    private String gmail;
    private String roomType;
    private LocalDate bookDate;
    private LocalDate inDate;
    private LocalDate outDate;
    private String room;

    // Constructors
    public Customerdb(int id, String name, String sex, String tel, String gmail, String roomType, LocalDate bookDate, LocalDate inDate, LocalDate outDate, String room) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.tel = tel;
        this.gmail = gmail;
        this.roomType = roomType;
        this.bookDate = bookDate;
        this.inDate = inDate;
        this.outDate = outDate;
        this.room = room;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public void setBookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public LocalDate getInDate() {
        return inDate;
    }

    public void setInDate(LocalDate inDate) {
        this.inDate = inDate;
    }

    public LocalDate getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDate outDate) {
        this.outDate = outDate;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
