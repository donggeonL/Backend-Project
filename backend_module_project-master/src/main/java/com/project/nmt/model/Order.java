package com.project.nmt.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Table(name = "\"order\"")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private LocalDate boughtDate;
    private int quantity;

    @ManyToOne
    private User user;

    @ManyToOne
    private Stock stock;

    @Builder
    public Order(int price, LocalDate boughtDate, int quantity, User user, Stock stock) {
        this.price = price;
        this.boughtDate = boughtDate;
        this.quantity = quantity;
        this.user = user;
        this.stock = stock;
    }
}
