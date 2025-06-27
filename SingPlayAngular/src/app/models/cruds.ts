import { Album } from "./album";
import { Cancion } from "./cancion";
import { Compositor } from "./compositor";

export interface CancionResponse {
  fecha: string;
  mensaje: string;
  status:string;
  cancion: Cancion[];
}

export interface AlbumResponse {
  fecha: string;
  mensaje: string;
  album: Album[];
  status:string;
}
export interface CompositorResponse {
  fecha: string;
  mensaje: string;
  compositores: Compositor[];
  status:string;
}