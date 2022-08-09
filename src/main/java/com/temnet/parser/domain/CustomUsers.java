package com.temnet.parser.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Objects;

/**
 * CustomUsers class using JPA to represent tables and rows as POJOs
 *
 * @author Temnet
 */
@Entity
@Table(name = "usr", schema = "ejabberd")
public class CustomUsers {
    /**
     * User id
     */
    private long id;
    /**
     * User name
     */
    private String username;
    /**
     * Foreign key to the Groups table storing the user's group ID
     * The field is serialized by ID
     *
     * @see Groups
     */
    @JsonIdentityReference(alwaysAsId = true)
    private Groups group;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomUsers that = (CustomUsers) o;

        if (id != that.id) return false;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @ManyToOne()
    @JoinColumn(name = "groups_id", referencedColumnName = "id", nullable = false)
    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }
}
