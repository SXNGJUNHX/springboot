package com.test.jwt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    @SequenceGenerator(name = "member_seq_generator", sequenceName = "seqMember", allocationSize = 1)
    private Long seq;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String role;

}














