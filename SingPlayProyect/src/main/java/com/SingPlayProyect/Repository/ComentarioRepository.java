package com.SingPlayProyect.Repository;

import com.SingPlayProyect.Model.CancionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SingPlayProyect.Model.ComentarioModel;

import java.util.Optional;


@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioModel ,Integer>{

   Optional<ComentarioModel> findByContenidoAndUsuario_IdUsuarioAndCancion_IdCancion(String contenido,Integer idUsuario,Integer idCancion);

}
