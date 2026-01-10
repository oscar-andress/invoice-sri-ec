package demo.invoice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demo.invoice.entity.InvoiceSequential;

public interface InvoiceSequentialRepository extends JpaRepository<InvoiceSequential, Long> {
    @Query(value = """ 
                   SELECT *
                   FROM tbl_invoice_sequential s
                   WHERE s.ruc = :ruc
                   AND s.document_type = :documentType
                   AND s.establishment_code = :establishmentCode
                   AND s.emission_point_code = :emissionPointCode 
                   """
           , nativeQuery = true)
    Optional<InvoiceSequential> queryFindNextInvoiceSequential(@Param("ruc") String ruc,
                                                     @Param("documentType") String documentType,
                                                     @Param("establishmentCode") String establishmentCode,
                                                     @Param("emissionPointCode") String emissionPointCode);
}
