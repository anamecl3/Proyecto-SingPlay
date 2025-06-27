import { Album } from "./album";
import { Compositor } from "./compositor";

export class Cancion {

    idCancion!: number;
    titulo!: string;
    duracion!: string;
    genero!: string;
    urlAudio!: string;
    album!: Album;
    compositor!: Compositor;
    activo!: boolean;
}
export class CancionDTO {

    tituloCancion!: string;
    duracionCancion!: string;
    generoCancion!: string;
    urlCancion!: string;
    idAlbum!: number;
    idCompositor!: number;
}