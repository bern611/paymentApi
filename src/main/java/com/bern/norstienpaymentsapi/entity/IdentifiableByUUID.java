/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.entity;

import java.util.UUID;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/**
 *
 * @author unknown
 */
@MappedSuperclass
public abstract class IdentifiableByUUID{

    /*@NaturalId
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")*/
    //@NaturalId
    protected UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @PrePersist
    void prePersist() {
        if (getUuid() == null) {
            setUuid(UUID.randomUUID());
        }
    }
}
