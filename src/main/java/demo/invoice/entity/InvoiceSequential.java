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
@Table(name = "tbl_invoice_sequential")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InvoiceSequential {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tblInvoiceSequentialSeq")
    @SequenceGenerator(name = "tblInvoiceSequentialSeq", sequenceName = "tbl_invoice_sequential_seq", allocationSize = 1)
    @Column(name = "id_invoice_sequencial")
    private Long idInvoiceSequencial;

    @Column(name = "ruc", nullable = false, length = 13)
    private String ruc;

    @Column(name = "document_type", nullable = false, length = 2)
    private String documentType;

    @Column(name = "establishment_code", nullable = false, length = 3)
    private String establishmentCode;

    @Column(name = "emission_point_code", nullable = false, length = 3)
    private String emissionPointCode;

    @Column(name = "last_sequential", nullable = false)
    private Integer lastSequential;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
