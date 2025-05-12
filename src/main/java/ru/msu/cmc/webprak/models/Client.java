package ru.msu.cmc.webprak.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Client implements Common<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NonNull
    private Integer id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "patron")
    private String patron;
}