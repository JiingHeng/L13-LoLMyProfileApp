package sg.edu.rp.c346.id20013327.lolmyprofile;

import java.io.Serializable;

public class Champions implements Serializable {

    private int id;
    private String name;
    private String role;
    private int star;

    public Champions(int id, String name, String role, int star) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.star = star;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "Champions{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", star=" + star +
                '}';
    }
}
