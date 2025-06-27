import { Component, OnInit } from '@angular/core';
import { Compositor } from '../../models/compositor';
import { CompositorService } from '../../service/compositor-service';

@Component({
  selector: 'app-compositor-component',
  standalone: false,
  templateUrl: './compositor-component.html',
  styleUrl: './compositor-component.css'
})
export class CompositorComponent implements OnInit {


  compositores : Compositor[] = []

  constructor( private compositorservice : CompositorService){}


  ngOnInit(): void {
   this.listarCompositores();
  }
listarCompositores() {
  this.compositorservice.listarCompositores().subscribe({
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
