import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comentario } from '../models/comentario';

@Injectable({
  providedIn: 'root'
})
export class ComentarioService {

  private apiUrl = 'https://localhost:8080/api/comentario'


  constructor(private http: HttpClient) { }

  listarComentarios(): Observable<any> {
    return this.http.get(`${this.apiUrl}/listar`);
  }

  obtenerComentarios(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  agregarComentarios(comentar: Comentario): Observable<any> {
    return this.http.post(this.apiUrl, comentar);
  }

  editarComentarios(id: number, comentar: Comentario): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, comentar);
  }

  eliminarComentarios(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }


}
