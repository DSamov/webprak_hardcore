package ru.msu.cmc.webprak.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "services")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Service implements Common<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "cost")
    private Float cost;
}