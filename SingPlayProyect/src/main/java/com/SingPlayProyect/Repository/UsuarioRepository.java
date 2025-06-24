package com.SingPlayProyect.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SingPlayProyect.Model.UsuarioModel;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

	
	Optional<UsuarioModel> findOneByEmail(String email);
	
	
}
