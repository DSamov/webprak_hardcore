package ru.msu.cmc.webprak_hardcore.models;

import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ClientInstanceID implements Serializable {
    @Column(name = "id", nullable = false)
    private Integer clientId;

    @Column(name = "phone", nullable = false)
    private Integer phoneId;


    public ClientInstanceID() {
    }

    public ClientInstanceID(Integer clientId, Integer phoneId) {
        this.clientId = clientId;
        this.phoneId = phoneId;
    }
}