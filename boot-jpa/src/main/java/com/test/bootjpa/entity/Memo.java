package com.test.bootjpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@Table(name = "tblMemo") //tbl_address
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memo_seq_generator")
    @SequenceGenerator(name = "memo_seq_generator", sequenceName = "seqMemo", allocationSize = 1)
    private Long seq;
    private String memo;
    private LocalDate regdate;
    private Long aseq;

}
