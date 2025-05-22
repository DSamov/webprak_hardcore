package ru.msu.cmc.webprak.models;

import jakarta.persistence.*;
//import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@Entity
@Table(name = "instances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Instance implements Common<InstanceID> {
    @EmbeddedId
    private InstanceID id = new InstanceID();

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id", nullable = false)
    @NonNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client clients;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id", nullable = false)
    @NonNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @ManyToOne
    @MapsId("servId")
    @JoinColumn(name = "service_id", nullable = false)
    @NonNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Service services;

    @Column(name = "start_date", nullable = false)
    @NonNull
    private java.sql.Date start;

    @Column(name = "finish_date")
    private Date finish;
}