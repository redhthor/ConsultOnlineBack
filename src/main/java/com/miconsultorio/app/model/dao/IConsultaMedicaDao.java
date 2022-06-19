package com.miconsultorio.app.model.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.miconsultorio.app.model.entities.ConsultaMedica;

public interface IConsultaMedicaDao extends ReactiveMongoRepository<ConsultaMedica, String>{

}
