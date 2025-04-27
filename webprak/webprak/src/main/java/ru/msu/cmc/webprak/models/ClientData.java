package ru.msu.cmc.webprak.models;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "clientsdata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientData implements Common<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NonNull
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NonNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client clients;

    @Column(name = "person", nullable = false)
    @NonNull
    private String person;

    @Column(name = "address", nullable = false)
    @NonNull
    private String address;
}