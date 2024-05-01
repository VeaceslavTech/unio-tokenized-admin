package at.unio.admin.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import at.unio.admin.data.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}