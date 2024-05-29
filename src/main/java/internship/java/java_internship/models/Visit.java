package internship.java.java_internship.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Visit {
    private int id;
    private LocalDateTime date;
    private String client;
    private String catName;
    private int catAge;
    private CatColor catColor;

    public Visit(int id, LocalDateTime date, String client, String catName, int catAge, CatColor catColor) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.catName = catName;
        this.catAge = catAge;
        this.catColor = catColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatAge() {
        return catAge;
    }

    public void setCatAge(int catAge) {
        this.catAge = catAge;
    }

    public CatColor getCatColor() {
        return catColor;
    }

    public void setCatColor(CatColor catColor) {
        this.catColor = catColor;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", date=" + date +
                ", client='" + client + '\'' +
                ", catName='" + catName + '\'' +
                ", catAge=" + catAge +
                ", catColor=" + catColor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit visit)) return false;
        return id == visit.id && catAge == visit.catAge && Objects.equals(date, visit.date) && Objects.equals(client, visit.client) && Objects.equals(catName, visit.catName) && catColor == visit.catColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, client, catName, catAge, catColor);
    }
}
