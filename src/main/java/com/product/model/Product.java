
package com.product.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Cacheable
@Table(name = "products")
public class Product extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Column(nullable = false)
	public String name;

	@Column(length = 1000)
	public String description;

	@Column(nullable = false)
	public Double price;

	@Column(nullable = false)
	public Integer quantity;
}
