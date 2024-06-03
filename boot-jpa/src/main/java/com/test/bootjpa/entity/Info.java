package com.test.bootjpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Table(name = "tblInfo") //tbl_address
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Info {

    @Id
    private Long seq;

    private String school;
    private String country;


    @OneToOne
    @JoinColumn(name="seq")
    private Address address;

}














