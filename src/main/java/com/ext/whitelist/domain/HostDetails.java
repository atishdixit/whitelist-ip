package com.ext.whitelist.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class HostDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String belongsTo;

    @Column(nullable = false, unique = true)
    private String ipAddress;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private Instant createAt;

    private Instant updateAt;

    @Column(nullable = false)
    private long createdBy;
}
