package com.bolsaideas.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.bolsaideas.springboot.webflux.app.models.documents.Producto;

@Component
public interface ProductoDao extends ReactiveMongoRepository<Producto, String>{

}
