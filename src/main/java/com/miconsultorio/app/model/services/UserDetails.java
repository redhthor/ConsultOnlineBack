package com.miconsultorio.app.model.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miconsultorio.app.model.dao.IUsuarioDao;
import com.miconsultorio.app.model.entities.Usuario;

@Service
public class UserDetails implements UserDetailsService {
	@Autowired
	private IUsuarioDao userDao;
	private Logger logger = LoggerFactory.getLogger(UserDetails.class);
	
	@Override
	@Transactional(readOnly = true)
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Usuario user = userDao.findByCorreo(username).block();
		if(user == null) {
			logger.error("El usuario {} no existe", username);
			throw new UsernameNotFoundException("Usuario o contraseña no válidos, favor de verificar los datos");
		}
		List<GrantedAuthority> authorities = user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		return new User(user.getCorreo(), user.getPassword(), true, true, true, true, authorities);
	}
	
}
