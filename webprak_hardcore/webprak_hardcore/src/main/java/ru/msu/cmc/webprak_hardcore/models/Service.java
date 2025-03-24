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
@Table(name = "Services")
@Getter
@Setter
public class Service implements Common<Integer> {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cost")
    private Float cost;

    public Service() {
    }

    public Service(
            Integer id,
            String name,
            Float cost
    ) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}