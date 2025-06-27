import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cancion, CancionDTO } from '../models/cancion';
import { CancionResponse } from '../models/cruds';
import { map, tap,catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class CancionService {


  private apiUrl = 'http://localhost:8080/api/cancion'

  constructor(private http: HttpClient) {

  }

  listarCanciones(): Observable<Cancion[]> {
    console.log('test')
    return this.http.get<CancionResponse>(`${this.apiUrl}/listar`)
      .pipe(
        tap(response => console.log('Respuesta completa del API:', response)),
        map(response => response.cancion)
      );
  }

  obtenerCancion(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }


agregarCancion(cancionDTO: CancionDTO): Observable<any> {
  return this.http.post(`${this.apiUrl}`, cancionDTO).pipe(
    tap(response => console.log('✅ Respuesta del servidor (agregarCancion):', response)),
    catchError(error => {
      console.error('❌ Error del servidor (agregarCancion):', error);
      return throwError(() => error);
    })
  );
}

  editarCancion(id: number, cancion: CancionDTO): Observable<any> {
      return this.http.put(`${this.apiUrl}/${id}`, cancion);
    }

  eliminarCancion(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  desactivarCancion(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/desactivar/${id}`, null)
      .pipe(
        tap(response => console.log('✅ Respuesta de desactivar:', response))
      );
  }
  activarCancion(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/activar/${id}`, null)
      .pipe(
        tap(response => console.log('✅ Respuesta de activar:', response))
      );
  }

}
