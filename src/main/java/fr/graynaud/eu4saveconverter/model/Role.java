package fr.graynaud.eu4saveconverter.model;

import javax.persistence.*;

@Entity(name = "Role")
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "name", length = 32, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
