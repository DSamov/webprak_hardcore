package ru.msu.cmc.webprak_hardcore.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "Instances")
@Getter
@Setter
public class Instance implements Common<InstanceID> {
    @EmbeddedId
    private InstanceID id = new InstanceID();

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client clients;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @ManyToOne
    @MapsId("servId")
    @JoinColumn(name = "service_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Service services;

    @Column(name = "start_date", nullable = false)
    private java.sql.Date start;

    @Column(name = "finish_date")
    private Date finish;

    @Override
    public InstanceID getId() {
        return id;
    }

    @Override
    public void setId(InstanceID id) {
        this.id = id;
    }
}