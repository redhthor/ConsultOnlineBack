package com.miconsultorio.app.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miconsultorio.app.model.dao.IUsuarioDao;
import com.miconsultorio.app.model.entities.Usuario;
import com.miconsultorio.app.model.services.IUsuarioService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	@Autowired
	private IUsuarioDao userDao;
	
	
	@Override
	public Mono<Usuario> save(Usuario usuario) {
		return userDao.save(usuario);
	}

	@Override
	public Flux<Usuario> findAll() {
		return userDao.findAll();
	}

	@Override
	public Mono<Usuario> findById(String id) {
		return userDao.findById(id);
	}
	
	@Override
	public Mono<Usuario> findByCorreo(String correo) {
		return userDao.findByCorreo(correo);
	}

}
