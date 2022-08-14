package com.temnet.parser.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SrUser class using JPA to represent tables and rows as POJOs
 *
 * @author Temnet
 */
@Entity
@Table(name = "sr_user", schema = "ejabberd")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SrUser {
    /**
     * User id
     */
    private Long id;
    /**
     * Jabber ID
     */
    private String jid;
    /**
     * Group
     */
    private String grp;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "jid", nullable = false, length = 191)
    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    @Basic
    @Column(name = "grp", nullable = false, length = 191)
    public String getGrp() {
        return grp;
    }

    public void setGrp(String grp) {
        this.grp = grp;
    }

}
