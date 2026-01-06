package demo.invoice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_issuer_config")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class IssuerConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tblIssuerConfigSec")
    @SequenceGenerator(name = "tblIssuerConfigSec", sequenceName = "tbl_issuer_config_sec", allocationSize = 1)
    @Column(name = "id_issuer_config")
    private Integer idIssuerConfig;

    @Column(name = "id_issuer", nullable = false)
    private Integer idIssuer;

    @Column(name = "environment", nullable = false, length = 1)
    private String environment;

    @Column(name = "emission_type", nullable = false, length = 1)
    private String emissionType;

    @Column(name = "establishment_code", nullable = false, length = 3)
    private String establishmentCode;

    @Column(name = "emission_point_code", nullable = false, length = 3)
    private String emissionPointCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
