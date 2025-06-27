import { Comentarios } from './paginas/comentarios/comentarios';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Principal } from './paginas/principal/principal';
import { Nosotros } from './paginas/nosotros/nosotros';
import { Conciertos } from './paginas/conciertos/conciertos';
import { Contactanos } from './paginas/contactanos/contactanos';
import { AlbumComponent } from './crud/album-component/album-component';
import { CompositorDosComponent } from './crudDos/compositor-dos-component/compositor-dos-component';

const routes: Routes = [


  //{ path: '', redirectTo: 'principal', pathMatch: 'full' },
 // { path : 'principal', component: Principal },
  //{ path: 'album', component: AlbumComponent },
  //{ path: 'nosotros', component: Nosotros },
  //{ path: 'comentarios', component: Comentarios },
  //{ path: 'conciertos', component: Conciertos },
  //{ path: 'contactanos', component: Contactanos },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
