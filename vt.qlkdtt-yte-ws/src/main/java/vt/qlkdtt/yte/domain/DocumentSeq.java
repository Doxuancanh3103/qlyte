package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DOCUMENT_SEQ")
public class DocumentSeq {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
}
