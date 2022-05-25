package com.javafxcalc.entities;

import javax.persistence.*;

@Entity
@Table(name = "HISTORY")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TXT", length = 10_000)
    @Lob
    private String text;

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public HistoryEntity() {}
    public HistoryEntity(String text) {
        this.text = text;
    }

}
