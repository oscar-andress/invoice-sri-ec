package demo.invoice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demo.invoice.entity.InvoiceSequential;

public interface InvoiceSequentialRepository extends JpaRepository<InvoiceSequential, Integer> {
    @Query(value = "SELECT *\n"+
                   "FROM tbl_invoice_sequential s\n"+
                   "WHERE s.ruc = :ruc\n"+
                   "AND s.document_type = :documentType\n"+
                   "AND s.establishment_code = :establishmentCode\n"+
                   "AND s.emission_point_code = :emissionPointCode\n"
           , nativeQuery = true)
    Optional<InvoiceSequential> queryFindNextInvoiceSequential(@Param("ruc") String ruc,
                                                     @Param("documentType") String documentType,
                                                     @Param("establishmentCode") String establishmentCode,
                                                     @Param("emissionPointCode") String emissionPointCode);
}
