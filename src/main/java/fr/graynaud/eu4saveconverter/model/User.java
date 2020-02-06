package fr.graynaud.eu4saveconverter.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "login", length = 64, nullable = false, unique = true, updatable = false)
    private String login;

    @Column(name = "password_hash", length = 60, nullable = false)
    private String passwordHash;

    @Column(name = "reset_key", length = 30, unique = true)
    private String resetKey;

    @Column(name = "reset_date")
    private Date resetDate;

    @Column(name = "last_connection_date")
    private Date lastConnectionDate;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Campaign> campaigns;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public Date getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(Date lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }

        this.roles.add(role);
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
    }
}
