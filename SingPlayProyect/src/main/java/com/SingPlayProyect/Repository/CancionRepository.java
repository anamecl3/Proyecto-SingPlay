package com.SingPlayProyect.Repository;

import com.SingPlayProyect.Model.AlbumModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SingPlayProyect.Model.CancionModel;

import java.util.Optional;

@Repository
public interface CancionRepository  extends JpaRepository< CancionModel,Integer>{
    Optional<CancionModel> findByTituloAndCompositor_IdCompositorAndAlbum_IdAlbum
            (String titulo,Integer idCompositor,Integer idAlbum);


}
