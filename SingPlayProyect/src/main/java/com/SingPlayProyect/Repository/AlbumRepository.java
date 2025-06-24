package com.SingPlayProyect.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SingPlayProyect.Model.AlbumModel;

import java.util.Optional;


@Repository
public interface AlbumRepository  extends JpaRepository<AlbumModel,Integer >{

    Optional<AlbumModel> findByTituloAndCantanteIdCantante(String titulo, Integer idCantante);

}
