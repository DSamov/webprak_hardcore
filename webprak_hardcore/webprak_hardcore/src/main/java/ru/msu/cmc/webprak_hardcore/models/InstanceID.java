package ru.msu.cmc.webprak_hardcore.models;

import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class InstanceID implements Serializable {
    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "service_id", nullable = false)
    private Integer servId;

    public InstanceID() {
    }

    public InstanceID(Integer clientId, Integer employeeId, Integer servId) {
        this.clientId = clientId;
        this.employeeId = employeeId;
        this.servId = servId;
    }
}