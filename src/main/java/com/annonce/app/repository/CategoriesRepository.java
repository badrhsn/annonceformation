package com.annonce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.annonce.app.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long>{

}
