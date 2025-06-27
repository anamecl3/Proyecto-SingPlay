import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cantante } from '../models/cantante';

@Injectable({
  providedIn: 'root'
})
export class CantanteService {


  private apiUrl = 'https://localhost:8080/api/cantante'

  constructor(private http: HttpClient) { }


  listarCantante(): Observable<any> {
    return this.http.get(`${this.apiUrl}/listar`);
  }

  obtenerCantante(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  agregarCantante(cantante: Cantante): Observable<any> {
    return this.http.post(this.apiUrl, cantante);
  }

  editarCantante(id: number, cantante: Cantante): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, cantante);
  }

  eliminarCantante(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }


}
