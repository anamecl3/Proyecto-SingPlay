import { Cantante } from "./cantante";

export class Album {

  idAlbum! : number;
  titulo! : string ; 
  fechaLanzamiento! : string ;
  imagenUrl! : string ; 
  cantante! : Cantante ;
  activo!: boolean;

}
