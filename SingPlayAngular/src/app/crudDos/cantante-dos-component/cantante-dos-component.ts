import { Component, OnInit } from '@angular/core';
import { Cantante } from '../../models/cantante';
import { CantanteService } from '../../service/cantante-service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-cantante-dos-component',
  standalone: false,
  templateUrl: './cantante-dos-component.html',
  styleUrl: './cantante-dos-component.css'
})
export class CantanteDosComponent implements OnInit{

  cantante : Cantante = new Cantante()
  editMode : boolean = false 

  constructor(
    private cantanteService: CantanteService,
        private route : ActivatedRoute,
    private router : Router
  ){}

  ngOnInit(): void {
      const id = this.route.snapshot.paramMap.get('id');
      if(id){
        this.editMode = true;
        this.cantanteService.obtenerCantante(+id).subscribe((res) => {
          this.cantante = res.data ||  res ;
        })
      }
  }



  guardar(){
    if(this.editMode && this.cantante.idCantante){
      this.cantanteService.editarCantante(this.cantante.idCantante , this.cantante)
      .subscribe( () => { this.router.navigate(['/'])})
    }else{
      this.cantanteService.agregarCantante(this.cantante)
      .subscribe( () => { this.router.navigate(['/'])})
    }
  }



  eliminar(){
    if(this.cantante.idCantante){
      this.cantanteService.eliminarCantante(this.cantante.idCantante)
      .subscribe( () => {this.router.navigate(['/'])})
    }
  }

}
