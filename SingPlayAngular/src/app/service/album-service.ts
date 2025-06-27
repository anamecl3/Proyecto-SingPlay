import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { Album } from '../models/album';
import { AlbumResponse } from '../models/cruds';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {

  private apiUrl = 'http://localhost:8080/api/album'

  constructor(private http: HttpClient) { }


  listarAlbums(): Observable<Album[]> {
    console.log('📡 solicitando álbumes...');
    return this.http.get<AlbumResponse>(`${this.apiUrl}/listar`)
      .pipe(
        tap(response => console.log('✅ Respuesta completa del API:', response)),
        map(response => response.album)
      );
  }
  obtenerAlbum(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  agregarAlbum(album: Album): Observable<any> {
    return this.http.post(this.apiUrl, album);
  }

  editarAlbum(id: number, album: Album): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, album);
  }

  eliminarAlbum(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

}
