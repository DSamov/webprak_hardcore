package ru.msu.cmc.webprak.models;

import lombok.*;

import javax.persistence.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeInstanceID implements Serializable {
    private Integer id;
    private String phone;
}