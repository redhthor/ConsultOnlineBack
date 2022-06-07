package com.miconsultorio.app.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.miconsultorio.app.model.entities.Usuario;
import com.miconsultorio.app.model.services.IUsuarioService;

@Component
public class ClaimsToken implements TokenEnhancer {
	@Autowired
	private IUsuarioService userService;
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new LinkedHashMap<>();
		Usuario user = userService.findByCorreo(authentication.getName()).block();
		if(user == null) {
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
			return accessToken;
		}
		info.put("nombre", user.getNombre());
		info.put("apellidoPaterno", user.getApellidoPaterno());
		info.put("apellidoMaterno", user.getApellidoMaterno());
		info.put("idUsuario", user.getId());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
	
}
