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
@Table(name = "tbl_issuer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Issuer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tblIssuerSec")
    @SequenceGenerator(name = "tblIssuerSec", sequenceName = "tbl_issuer_sec", allocationSize = 1)
    @Column(name = "id_issuer")
    private Integer idIssuer;

    @Column(name = "ruc", length = 13)
    private String ruc;

    @Column(name = "legal_name", length = 200)
    private String legalName;

    @Column(name = "head_office_address", length = 300)
    private String headOfficeAddress;

    @Column(name = "accounting_required")
    private Boolean accountingRequired;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "active")
    private Boolean active = true;
}
