import { Cliente } from "./cliente";

export class Statistiche {
    numeroTotaleCliente!: number;
    clienteSaldoMaggiore!: Cliente[];
    dataUltimaTransazione!: Date;

    // totale transazioni
    numeroTotaleTransazioni!: number;
    sommaTotaleTransazioni!: number;

    saldoMedioConti!: number;

    // dati per cliente
    
    contiPerCliente!: Map<number, number>;
    cartePerCliente!: { [key: string]: number };
    importoTotPrestitiPerCliente!: { [key: string]: number };
    importoTotPagamentiPerCliente!: { [key: string]: number };
    

    numeroTransazioniPerTipo!: { [key: string]: number };
    mediaTransazioniPerCliente!: number;
    totaleImportoTranszioniPerMese!: Map<string, number>;
}


export enum TipoTransazione {
    PAGAMENTO = 'PAGAMENTO',
    PRESTITO = 'PRESTITO',
  }
