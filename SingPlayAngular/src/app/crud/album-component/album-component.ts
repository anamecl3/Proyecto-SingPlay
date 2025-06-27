import { Component, OnInit } from '@angular/core';
import { Album } from '../../models/album';
import { AlbumService } from '../../service/album-service';

@Component({
  selector: 'app-album-component',
  standalone: false,
  templateUrl: './album-component.html',
  styleUrl: './album-component.css'
})
export class AlbumComponent implements OnInit{

  albums : Album[] = []

  constructor( private albumservice : AlbumService){}


  ngOnInit(): void {
      this.listarAlbums();
  }
 listarAlbums() {
  this.albumservice.listarAlbums().subscribe({
    next: (data) => {
      console.log('🎵 Lista de álbumes obtenida:', data);
      this.albums = data;
    },
    error: (err) => {
      console.error('❌ Error al obtener álbumes:', err);
    }
  });
}
}
