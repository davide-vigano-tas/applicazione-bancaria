import { Cliente } from './cliente';
import { TipoTransazione } from './tipo-transazione';



export class Statistiche {
    numeroTotaleCliente!: number;
    clienteSaldoMaggiore!: Cliente[];
    dataUltimaTransazione!: Date;

    numeroTotaleTransazioni!: number;
    sommaTotaleTransazioni!: number;

    saldoMedioConti!: number;

    contiPerCliente!: Map<number, number>;
    cartePerCliente!: Map<number, number>;

    importoTotPrestitiPerCliente!: Map<number, number>;
    importoTotPagamentiPerCliente!: Map<number, number>;

    numeroTransazioniPerTipo!: Map<TipoTransazione, number>;

    mediaTransazioniPerCliente!: number;
    totaleImportoTransazioniPerMese!: Map<string, number>;

} 
