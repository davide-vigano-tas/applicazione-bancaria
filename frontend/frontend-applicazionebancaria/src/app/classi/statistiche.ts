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
    
    contiPerCliente!: Map<number, number>; // conti per cliente
    // cartePerCliente!: Map<number, number>; // carte per cliente
    cartePerCliente!: { [key: string]: number }; // carte per cliente
    importoTotPrestitiPerCliente!: { [key: string]: number };  // importo totale prestiti per cliente
    importoTotPagamentiPerCliente!: { [key: string]: number }; // umporto totale pagamenti per cliente
    

    numeroTransazioniPerTipo!: Map<TipoTransazione, number>; // transazioni per tipo

    mediaTransazioniPerCliente!: number;

    totaleImportoTranszioniPerMese!: Map<string, number>; // importo per mese
}


export enum TipoTransazione {
    PAGAMENTO = 'PAGAMENTO',
    PRESTITO = 'PRESTITO',
  }
