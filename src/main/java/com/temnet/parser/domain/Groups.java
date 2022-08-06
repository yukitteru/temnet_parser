package com.temnet.parser.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.temnet.parser.util.EntityIdResolver;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        scope = Groups.class,
        resolver = EntityIdResolver.class,
        property = "id"
)
public class Groups {
    private long id;
    private String name;
    private Collection<CustomUsers> customUsers;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Groups groups = (Groups) o;

        if (id != groups.id) return false;
        return Objects.equals(name, groups.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    public Collection<CustomUsers> getCustomUsers() {
        return customUsers;
    }

    public void setCustomUsers(Collection<CustomUsers> customUsers) {
        this.customUsers = customUsers;
    }
}
