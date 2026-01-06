package demo.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.invoice.entity.Issuer;

public interface IssuerRepository extends JpaRepository<Issuer, Integer>{
    Issuer findByActiveTrue();
}
