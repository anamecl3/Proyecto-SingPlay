import { Component, OnInit } from '@angular/core';
import { Cantante } from '../../models/cantante';
import { CantanteService } from '../../service/cantante-service';

@Component({
  selector: 'app-cantante-component',
  standalone: false,
  templateUrl: './cantante-component.html',
  styleUrl: './cantante-component.css'
})
export class CantanteComponent implements OnInit{

  cantantes : Cantante[] = []

  constructor( private cantanteService: CantanteService) { }

  ngOnInit(): void {
    this.cantanteService.listarCantante().subscribe({
      next: (data) => (this.cantantes = data),
      error: (err) => console.error('Error al obtener cantantes', err)
    });
  }



}



