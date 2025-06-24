package com.SingPlayProyect.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SingPlayProyect.Model.CantanteModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CantanteRepository extends JpaRepository<CantanteModel , Integer > {

    Optional<CantanteModel> findByNombreAndGenero(String Nombre,String Genero);

}
