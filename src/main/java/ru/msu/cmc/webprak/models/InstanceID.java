package ru.msu.cmc.webprak.models;

import lombok.*;

import javax.persistence.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InstanceID implements Serializable {
    @Column(name = "client_id", nullable = false)
    @NonNull
    private Integer clientId;

    @Column(name = "employee_id", nullable = false)
    @NonNull
    private Integer employeeId;

    @Column(name = "service_id", nullable = false)
    @NonNull
    private Integer servId;
}