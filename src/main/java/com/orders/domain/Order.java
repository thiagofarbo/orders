package com.orders.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = Order.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "orders";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_" + TABLE_NAME)
    @SequenceGenerator(name = "gen_" + TABLE_NAME, sequenceName = "sq_" + TABLE_NAME, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String externalId;

    @Column(nullable = false)
    private Double totalValue;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

