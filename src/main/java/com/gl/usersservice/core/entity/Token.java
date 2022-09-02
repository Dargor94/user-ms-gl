package com.gl.usersservice.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Token implements Serializable {

    private static final long serialVersionUID = 7054468807469864L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    @Column(nullable = false)
    private String userName;

}
