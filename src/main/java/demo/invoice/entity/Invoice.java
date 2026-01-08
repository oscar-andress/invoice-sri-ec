package demo.invoice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "tbl_invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tblInvoiceSec")
    @SequenceGenerator(name = "tblInvoiceSec", sequenceName = "tbl_invoice_sec", allocationSize = 1)
    @Column(name = "id_invoice")
    private Long idInvoice;

    @Column(name = "access_key", nullable = false, length = 100)
    private String accessKey;

    @Column(name = "sequential", nullable = false, length = 9)
    private String sequential;

    @Column(name = "id_issuer", nullable = false)
    private Integer idIssuer;

    @Column(name = "buyer_identification", nullable = false, length = 13)
    private String buyerIdentification;

    @Column(name = "buyer_name", nullable = false, length = 200)
    private String buyerName;

    @Column(name = "buyer_id_type", nullable = false, length = 2)
    private String buyerIdType;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "environment", nullable = false, length = 1)
    private String environment;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "total_without_taxes", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalWithoutTaxes;

    @Column(name = "total_tax_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalTaxAmount;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "unsigned_xml", columnDefinition = "TEXT")
    private String unsignedXml;

    @Column(name = "signed_xml", columnDefinition = "TEXT")
    private String signedXml;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "authorization_date")
    private LocalDate autorizationDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
