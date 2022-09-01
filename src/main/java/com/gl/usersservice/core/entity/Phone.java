package com.gl.usersservice.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table
public class Phone implements Serializable {

    private static final long serialVersionUID = -5429240328242025973L;

    @Id
    @Column(nullable = false)
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID phoneId;

    private long number;

    private int cityCode;

    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

}