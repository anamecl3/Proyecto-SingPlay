import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Principal } from './paginas/principal/principal';
import { Nosotros } from './paginas/nosotros/nosotros';
import { Conciertos } from './paginas/conciertos/conciertos';
import { Menu } from './reusables/menu/menu';
import { Carrousel } from './reusables/carrousel/carrousel';
import { Footer } from './reusables/footer/footer';
import { Contactanos } from './paginas/contactanos/contactanos';
import { Comentarios } from './paginas/comentarios/comentarios';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Login } from './auth/login/login';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './helpers/auth.guard';
import { AuthInterceptor } from './helpers/auth.interceptor';
import { CompositorComponent } from './crud/compositor-component/compositor-component';
import { CantanteComponent } from './crud/cantante-component/cantante-component';
import { AlbumComponent } from './crud/album-component/album-component';
import { CancionComponent } from './crud/cancion-component/cancion-component';
import { ComentarioComponent } from './crud/comentario-component/comentario-component';
import { AlbumDosComponent } from './crudDos/album-dos-component/album-dos-component';
import { CancionDosComponent } from './crudDos/cancion-dos-component/cancion-dos-component';
import { CantanteDosComponent } from './crudDos/cantante-dos-component/cantante-dos-component';
import { CompositorDosComponent } from './crudDos/compositor-dos-component/compositor-dos-component';
import { NgSelectModule } from '@ng-select/ng-select';


@NgModule({
  declarations: [
    App,
    Principal,
    Nosotros,
    Conciertos,
    Menu,
    Carrousel,
    Footer,
    Contactanos,
    Comentarios,
    Login,
    CompositorComponent,
    CantanteComponent,
    AlbumComponent,
    CancionComponent,
    ComentarioComponent,
    AlbumDosComponent,
    CancionDosComponent,
    CantanteDosComponent,
    CompositorDosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgSelectModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      { path: 'principal', component: Principal, canActivate: [AuthGuard] },
      { path: 'login', component: Login },
      { path: '', component: Login },
    ]),
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true
  }
  ],



  bootstrap: [App]
})
export class AppModule { }
