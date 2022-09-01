package com.gl.usersservice.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table
public class Customer implements Serializable {

    private static final long serialVersionUID = -4889621880891239838L;

    @Id
    @Column(nullable = false)
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID customerId;

    @Column(nullable = false)
    private String email;

    private String name;

    @Column(nullable = false)
    private String password;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

    private boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Phone> phones;

}

