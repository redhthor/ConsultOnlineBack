package com.miconsultorio.app.model.services;

import com.miconsultorio.app.model.entities.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUsuarioService {

	public Mono<Usuario> save(Usuario usaurio);

	public Flux<Usuario> findAll();

	public Mono<Usuario> findById(String id);

	public Mono<Usuario> findByCorreo(String correo);

}
