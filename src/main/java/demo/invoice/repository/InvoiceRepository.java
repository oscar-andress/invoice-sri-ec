package demo.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.invoice.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
}
