package ru.msu.cmc.webprak_hardcore.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;
import java.util.Date;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "EmployeeInstances")
@Getter
@Setter
public class EmployeeInstance implements Common<EmployeeInstanceID> {
    @EmbeddedId
    private EmployeeInstanceID id = new EmployeeInstanceID();

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee emp;

    @MapsId("phoneId")
    @Column(name = "phone", nullable = false)
    private char[] phoneNum = new char[11];

    @Column(name = "email")
    private String email;
}