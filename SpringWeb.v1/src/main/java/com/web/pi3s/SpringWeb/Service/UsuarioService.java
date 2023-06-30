package com.web.pi3s.SpringWeb.Service;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;



@Service
public interface UsuarioService {
@Bean
	 void apagarUsuarioPorId(UUID id);
	
}
