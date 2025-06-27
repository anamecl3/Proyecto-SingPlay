import { Component, OnInit } from '@angular/core';
import { Compositor } from '../../models/compositor';
import { CompositorService } from '../../service/compositor-service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-compositor-dos-component',
  standalone: false,
  templateUrl: './compositor-dos-component.html',
  styleUrl: './compositor-dos-component.css'
})
export class CompositorDosComponent implements OnInit {

  compositor : Compositor = new Compositor();
  editMode : boolean = false 
  constructor(
    private compositorservice : CompositorService ,
    private route : ActivatedRoute,
    private router : Router
  ){}


  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id'); 
    if(id){
      this.editMode = true ;
      this.compositorservice.obtenercompositor(+id).subscribe( (res) => {
        this.compositor = res.data || res ;
      }) 
    }

  }


  guardar() : void{
    if(this.editMode && this.compositor.idCompositor){
      this.compositorservice.editarcompositor(this.compositor.idCompositor, this.compositor)
      .subscribe( () => { this.router.navigate(['/'])})
    }else {
      this.compositorservice.agregarcompositor(this.compositor)
      .subscribe( () => this.router.navigate(['/']))
    }
  }


  eliminar() : void { 
    if(this.compositor.idCompositor){
      this.compositorservice.eliminarcompositor(this.compositor.idCompositor)
        .subscribe( () => { this.router.navigate(['/'])})
    }
  }

}
