import { Component } from '@angular/core';
import { Album } from '../../models/album';
import { AlbumService } from '../../service/album-service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-album-dos-component',
  standalone: false,
  templateUrl: './album-dos-component.html',
  styleUrl: './album-dos-component.css'
})
export class AlbumDosComponent {


  album : Album = new Album()
  editMode : boolean = false 

  constructor(
    private albumservice : AlbumService,
    private route : ActivatedRoute,
    private router : Router
  ){}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id){
      this.editMode = true
      this.albumservice.obtenerAlbum(+id).subscribe( (res) => {
        this.album = res.data || res ; 
      })
    }
  }


  guardar() : void {
    if(this.editMode && this.album.idAlbum){
      this.albumservice.editarAlbum(this.album.idAlbum , this.album)
      .subscribe( () => {this.router.navigate(['/'])})
    }else {
      this.albumservice.agregarAlbum(this.album)
      .subscribe( () => {this.router.navigate(['/'])})
    }
  }


  eliminar() : void {
    if(this.album.idAlbum){
      this.albumservice.eliminarAlbum(this.album.idAlbum)
      .subscribe( () => {this.router.navigate(['/'])})
    }
  }

}
