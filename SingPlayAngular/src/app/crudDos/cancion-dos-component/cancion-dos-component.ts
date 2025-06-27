
import { Component, OnInit } from '@angular/core';
import { Cancion } from '../../models/cancion';
import { CancionService } from '../../service/cancion-service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-cancion-dos-component',
  standalone: false,
  templateUrl: './cancion-dos-component.html',
  styleUrl: './cancion-dos-component.css'
})
export class CancionDosComponent implements OnInit{


  cancion : Cancion = new Cancion()
  editMode : boolean = false 

  constructor(
    private cancionservice : CancionService,
    private route : ActivatedRoute,
    private router : Router
  ){}


  ngOnInit(): void {
      const id = this.route.snapshot.paramMap.get('id');
      if(id){
        this.editMode = true ;
        this.cancionservice.obtenerCancion(+id).subscribe( (res) => {
          this.cancion = res.data || res; 
        })
      }
  }



  guardar() :void {
    if(this.editMode && this.cancion.idCancion){
    //
    }else {
//      this.cancionservice.agregarCancion( null)
   //   .subscribe( () => { this.router.navigate(['/'])})
    }
  }


  eliminar() : void{
    if(this.cancion.idCancion){
      this.cancionservice.eliminarCancion( this.cancion.idCancion)
      .subscribe( () => { this.router.navigate([''])})
    }
  }




}
