package ru.msu.cmc.webprak.models;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "employeeinstances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(EmployeeInstanceID.class)
public class EmployeeInstance implements Common<EmployeeInstanceID> {
    @Id
    @Column(name = "id", nullable = false)
    @NonNull
    private Integer id;

    @Id
    @Column(name = "phone", nullable = false)
    @NonNull
    private String phone;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @Override
    public void setId(EmployeeInstanceID employeeInstanceID) {
    }

    @Override
    public EmployeeInstanceID getId() {
        return new EmployeeInstanceID(id, phone);
    }
}