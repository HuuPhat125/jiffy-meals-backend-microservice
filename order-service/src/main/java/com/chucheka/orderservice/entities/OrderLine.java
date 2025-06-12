package com.chucheka.orderservice.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "order_lines")
@EqualsAndHashCode
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;

    @Column(nullable = false)
    private String itemCode;

    private Integer quantity;

    private Double price; // Store price at order time

}
