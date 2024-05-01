package at.unio.admin.data.service;

import org.springframework.stereotype.Service;

import at.unio.admin.data.entity.Portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }


    public Optional<Portfolio> get(Long id) {
        return portfolioRepository.findById(id);
    }

    public Optional<Portfolio> getFirst(){
    	return portfolioRepository.findAll().stream().findFirst();
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Portfolio getPortfolio(Long id) {
        return portfolioRepository.findById(id).orElse(null);
    }

    public Portfolio savePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    public Page<Portfolio> list(Pageable pageable) {
        return portfolioRepository.findAll(pageable);
    }

    public Page<Portfolio> list(Pageable pageable, Specification<Portfolio> filter) {
        return portfolioRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) portfolioRepository.count();
    }

}
