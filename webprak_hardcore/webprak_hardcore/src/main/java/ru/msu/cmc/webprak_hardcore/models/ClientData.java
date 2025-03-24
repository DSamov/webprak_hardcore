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
@Table(name = "ClientsData")
@Getter
@Setter
public class ClientData implements Common<Integer> {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client clients;

    @Column(name = "person", nullable = false)
    private String person;

    @Column(name = "address", nullable = false)
    private String address;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}