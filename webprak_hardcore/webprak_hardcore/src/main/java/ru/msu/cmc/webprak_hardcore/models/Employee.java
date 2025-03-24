package ru.msu.cmc.webprak_hardcore.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.Date;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "Employee")
@Getter
@Setter
public class Employee implements Common<Integer> {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "patron")
    private String patron;

    @Column(name = "address")
    private String address;

    @Column(name = "education", nullable = false)
    private String education;

    @Column(name = "work_post", nullable = false)
    private String work_post;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}