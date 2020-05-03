package com.annonce.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annonce.app.model.Categories;
import com.annonce.app.repository.CategoriesRepository;

@Service
public class CategoriesService {
	@Autowired
	CategoriesRepository categoriesRepository;
	
	public List<Categories> findAllCategories() {
		return categoriesRepository.findAll();	}

}
