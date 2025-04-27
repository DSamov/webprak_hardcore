package ru.msu.cmc.webprak.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Employee implements Common<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "surname", nullable = false)
    @NonNull
    private String surname;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "patron")
    private String patron;

    @Column(name = "address")
    private String address;

    @Column(name = "education", nullable = false)
    @NonNull
    private String education;

    @Column(name = "work_post", nullable = false)
    @NonNull
    private String work_post;
}