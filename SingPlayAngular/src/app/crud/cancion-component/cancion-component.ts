import { Component, OnInit } from '@angular/core';
import { Cancion, CancionDTO } from '../../models/cancion';
import { CancionService } from '../../service/cancion-service';

import Modal from 'bootstrap/js/dist/modal';
import { Album } from '../../models/album';
import { Compositor } from '../../models/compositor';
import { AlbumService } from '../../service/album-service';
import { CompositorService } from '../../service/compositor-service';
import { HttpClient } from '@angular/common/http';
import { GeneroMusical } from '../../enums/generomusical.enum';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cancion-component',
  standalone: false,
  templateUrl: './cancion-component.html',
  styleUrl: './cancion-component.css'
})
export class CancionComponent implements OnInit {

  cancionForm: Cancion = new Cancion();
  idAlbumSeleccionado: number | null = null;
  idCompositorSeleccionado: number | null = null;
  modalModo: 'agregar' | 'editar' = 'agregar';
  modalRef: any;

  generos: string[] = GeneroMusical.values();
  canciones: Cancion[] = [];
  albums: Album[] = [];
  compositores: Compositor[] = [];

  constructor(
    private cancionservice: CancionService,
    private albumService: AlbumService,
    private compositorService: CompositorService,
    private http: HttpClient

  ) { }

  ngOnInit(): void {
    this.listarCanciones();
    this.listarAlbums();
    this.listarCompositores();

  }


  abrirModal(modo: 'agregar' | 'editar', cancion?: Cancion) {
    this.modalModo = modo;

    if (modo === 'editar' && cancion) {
      this.cancionForm = { ...cancion }; // Copia para editar
      this.idAlbumSeleccionado = cancion.album?.idAlbum ?? null;
      this.idCompositorSeleccionado = cancion.compositor?.idCompositor ?? null;
    } else {
      this.cancionForm = new Cancion(); // Limpia    
      this.idAlbumSeleccionado = null;
      this.idCompositorSeleccionado = null;
    }

    const modalElement = document.getElementById('cancionModal');
    this.modalRef = new Modal(modalElement);
    this.modalRef.show();
  }
  guardarCancion() {
    if (!this.cancionForm.titulo || !this.cancionForm.genero || !this.cancionForm.duracion || !this.cancionForm.urlAudio ||
      !this.idAlbumSeleccionado || !this.idCompositorSeleccionado) {
      Swal.fire({
        icon: 'error',
        title: 'Campos incompletos',
        text: '❌ Por favor completa todos los campos obligatorios.'
      });

      return;
    }

    this.cancionForm.album = this.albums.find(a => a.idAlbum === this.idAlbumSeleccionado) as Album;
    this.cancionForm.compositor = this.compositores.find(c => c.idCompositor === this.idCompositorSeleccionado) as Compositor;

    const dto: CancionDTO = {
      tituloCancion: this.cancionForm.titulo,
      duracionCancion: this.cancionForm.duracion,
      generoCancion: this.cancionForm.genero,
      urlCancion: this.cancionForm.urlAudio,
      idAlbum: this.idAlbumSeleccionado,
      idCompositor: this.idCompositorSeleccionado
    };

    if (this.modalModo === 'agregar') {
      this.agregarCancionBackend(dto);
    } else {
      this.editarCancionBackend(this.cancionForm.idCancion, dto);
    }

    this.modalRef.hide();
  }

  agregarCancionBackend(dto: CancionDTO) {
    this.cancionservice.agregarCancion(dto).subscribe({
      next: () => {
        Swal.fire('✅ Canción agregada', '', 'success');
        this.listarCanciones();
        this.modalRef.hide();
      },
      error: () => {
        Swal.fire('❌ Error al agregar canción', 'Inténtalo nuevamente.', 'error');
      }
    });
  }

  editarCancionBackend(id: number, dto: CancionDTO) {
    this.cancionservice.editarCancion(id, dto).subscribe({
      next: () => {
        Swal.fire('✅ Canción editada', '', 'success');
        this.listarCanciones();
        this.modalRef.hide();
      },
      error: () => {
        Swal.fire('❌ Error al editar canción', 'Inténtalo nuevamente.', 'error');
      }
    });
  }

  eliminarCancion(id: number) {
    Swal.fire({
      title: '¿Estás seguro de eliminar esta canción?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if (result.isConfirmed) {
        this.cancionservice.eliminarCancion(id).subscribe({
          next: () => {
            Swal.fire('✅ Canción eliminada', '', 'success');
            this.listarCanciones();
          },
          error: () => {
            Swal.fire('❌ Error al eliminar canción', 'Inténtalo nuevamente.', 'error');
          }
        });
      }
    });
  }
  desactivarCancion(cancion: Cancion) {
    Swal.fire({
      title: '¿Dar de baja la canción?',
      text: 'La canción quedará inactiva.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, dar de baja',
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if (result.isConfirmed) {
        this.cancionservice.desactivarCancion(cancion.idCancion).subscribe({
          next: () => {
            Swal.fire('✅ Canción desactivada', '', 'success');
            this.listarCanciones();
          },
          error: () => {
            Swal.fire('❌ Error al desactivar', 'Inténtalo nuevamente.', 'error');
          }
        });
      }
    });
  }

  activarCancion(cancion: Cancion) {
    Swal.fire({
      title: '¿Dar de alta la canción?',
      text: 'La canción volverá a estar activa.',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, activar',
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if (result.isConfirmed) {
        this.cancionservice.activarCancion(cancion.idCancion).subscribe({
          next: () => {
            Swal.fire('✅ Canción activada', '', 'success');
            this.listarCanciones();
          },
          error: () => {
            Swal.fire('❌ Error al activar', 'Inténtalo nuevamente.', 'error');
          }
        });
      }
    });
  }

  listarCanciones() {
    this.cancionservice.listarCanciones().subscribe({
      next: (data) => {
        console.log('🎵 Lista de canciones obtenida:', data);
        this.canciones = data;
      },
      error: (err) => {
        console.error('❌ Error al obtener canciones:', err);
      }
    });
  }
  listarAlbums() {
    this.albumService.listarAlbums().subscribe({
      next: (data) => {
        console.log('🎵 Lista de álbumes obtenida:', data);
        this.albums = data;
      },
      error: (err) => {
        console.error('❌ Error al obtener álbumes:', err);
      }
    });
  }

  listarCompositores() {
    this.compositorService.listarCompositores().subscribe({
      next: (data) => {
        console.log('🎼 Lista de compositores obtenida:', data);
        this.compositores = data;
      },
      error: (err) => {
        console.error('❌ Error al obtener compositores:', err);
      }
    });
  }


}
