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
@Table(name = "Clients")
@Getter
@Setter
public class Client implements Common<Integer> {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "patron")
    private String patron;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}