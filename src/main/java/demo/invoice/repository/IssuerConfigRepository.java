package demo.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.invoice.entity.IssuerConfig;

public interface IssuerConfigRepository extends JpaRepository<IssuerConfig, Integer> {
    IssuerConfig findByIdIssuer(int idIssuer);
}
