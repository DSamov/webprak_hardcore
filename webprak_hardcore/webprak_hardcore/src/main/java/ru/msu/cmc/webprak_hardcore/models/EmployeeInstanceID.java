package ru.msu.cmc.webprak_hardcore.models;

import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class EmployeeInstanceID implements Serializable {
    @Column(name = "id", nullable = false)
    private Integer employeeId;

    @Column(name = "phone", nullable = false)
    private Integer phoneId;


    public EmployeeInstanceID() {
    }

    public EmployeeInstanceID(Integer employeeId, Integer phoneId) {
        this.employeeId = employeeId;
        this.phoneId = phoneId;
    }
}