package demo.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demo.invoice.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
    @Query(value = """
                   SELECT * 
                   FROM tbl_invoice
                   WHERE status = :status
                         AND id_invoice in (:idInvoices) 
                         AND signed_xml IS NOT NULL 
                         AND signed_xml <> ''
                   ORDER BY issue_date desc
                   """
    , nativeQuery = true)
    List<Invoice> queryFindByIdInvoiceInAndStatus(@Param("idInvoices") List<Long> idInvoices,
                                    @Param("status") String status);
}
