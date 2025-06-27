import { Cancion } from "./cancion";
import { Usuario } from "./usuario";

export class Comentario {

    idComentario!: number;
    contenido!: string;
    fecha!: Date;
    usuario!: Usuario;
    cancion!: Cancion;

}
