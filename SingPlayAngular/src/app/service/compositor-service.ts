import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { Compositor } from '../models/compositor';
import { CompositorResponse } from '../models/cruds';

@Injectable({
  providedIn: 'root'
})
export class CompositorService {

  private apiUrl = 'http://localhost:8080/api/compositor'

  constructor(private http : HttpClient) { }


  listarCompositores(): Observable<Compositor[]> {
    console.log('📡 solicitando compositores...');
    return this.http.get<CompositorResponse>(`${this.apiUrl}/listar`)
      .pipe(
        tap(response => console.log('✅ Respuesta completa del API:', response)),
        map(response => response.compositores)
      );
  }

  obtenercompositor(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  agregarcompositor(compositor: Compositor): Observable<any> {
    return this.http.post(this.apiUrl, compositor);
  }

  editarcompositor(id: number, compositor: Compositor): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, compositor);
  }

  eliminarcompositor(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

}
