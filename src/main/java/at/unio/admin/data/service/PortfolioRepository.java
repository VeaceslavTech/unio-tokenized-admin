package at.unio.admin.data.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import at.unio.admin.data.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

	Page<Portfolio> findAll(Specification<Portfolio> filter, Pageable pageable);
}
