package ru.msu.cmc.webprak.models;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "clientinstances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(ClientInstanceID.class)
public class ClientInstance implements Common<ClientInstanceID> {
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
    private Client client;

    @Override
    public void setId(ClientInstanceID clientInstanceID) {
    }

    @Override
    public ClientInstanceID getId() {
        return new ClientInstanceID(id, phone);
    }
}