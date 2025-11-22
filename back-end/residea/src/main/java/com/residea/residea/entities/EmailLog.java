package com.residea.residea.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "EmailLog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmail")
    private Integer idEmail;

    @Column(length = 200)
    private String destinatario;

    @Column(length = 255)
    private String subject;

    @Column(length = 150)
    private String template;

    @Column(columnDefinition = "TEXT")
    private String variablesJson;

    @Column(length = 20)
    private String status; // PENDING | SENT | FAILED | BOUNCED

    private Integer attempts;

    @Column(columnDefinition = "TEXT")
    private String providerResponse;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
