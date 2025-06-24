package com.SingPlayProyect.Repository;

import com.SingPlayProyect.Model.CantanteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SingPlayProyect.Model.CompositorModel;

import java.util.Optional;


@Repository
public interface CompositorRepository extends JpaRepository<CompositorModel , Integer> {

    Optional<CompositorModel> findByNombre(String Nombre);

}
