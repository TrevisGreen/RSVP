package com.RSVP.rsvp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "events")
@NamedQueries({
        @NamedQuery(name = "findEventByCode", query = "select e from Event e where e.code = :code")
})
public class Event implements Serializable {

    @Id
    private String id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String code;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String code;
    @NotBlank
    @Column(nullable = false)
    private String name;
    private String description;
    @Temporal(javax.persistence.TemporalType.TIMESSTAMP)
    @Column(name = "date_", nullable = false)
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm zzz")
    private Date date = new Date();
    @Transient
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date startDate = new Date();
    @Transient
    private String time;
    @Transient
    private String timeZone;
    private String street;
    private String city;
    @Column(name = "state_")
    private String state;
    private String zip;
    private Integer seats = 0;
    @NotBlank
    @Column(nullable = false)
    private String hostName;
    @NotBlank
    private String hostPhone;
    @NotBlank
    @Email
    @Column(nullable = false)
    private String hostEmail;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreated;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastUpdated;
    @ManyToOne(optional = false)
    private User user;
    @Column(length = 32)
    private String contentType;
    @Column(length = 64)
    private String imageName;
    private Long imageSize;
    private byte[] imageDate;
    @Transient
    private MultipartFile imageFile;
    @Column(length = 32)
    private String status = Constants.NEW;

    // Default constructors
    public Event() {
    }

    // Getters and Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostPhone() {
        return hostPhone;
    }

    public void setHostPhone(String hostPhone) {
        this.hostPhone = hostPhone;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Long getImageSize() {
        return imageSize;
    }

    public void setImageSize(Long imageSize) {
        this.imageSize = imageSize;
    }

    public byte[] getImageDate() {
        return imageDate;
    }

    public void setImageDate(byte[] imageDate) {
        this.imageDate = imageDate;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        final Event other = (Event) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", code=" + code + ", name=" + name + ", description=" + description + ", date=" + date + ", startDate=" + startDate + ", time=" + time + ", timeZone=" + timeZone + ", street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip + ", seats=" + seats + ", hostName=" + hostName + ", hostPhone=" + hostPhone + ", hostEmail=" + hostEmail + ", dateCreated=" + dateCreated + ", lastUpdated=" + lastUpdated + ", user=" + user + ", contentType=" + contentType + ", imageName=" + imageName + ", imageSize=" + imageSize + ", status=" + status + '}';
    }
}
