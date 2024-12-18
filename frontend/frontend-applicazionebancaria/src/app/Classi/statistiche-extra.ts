import { DettTrans } from './dett-trans';

export enum StatoRichiestaPrestito {
    IN_ATTESA = 'IN_ATTESA',
    APPROVATO = 'APPROVATO',
    RIFIUTATO = 'RIFIUTATO',
}


export class StatisticheExtra {
    prestitiRichiestiPerStato!: Map<StatoRichiestaPrestito, number>;
	sommaPrestitiApprovatiPerCliente!: Map<number,number>;
	percentualeTransazioniAccredito!: number;
	dettagliTransazioniPerCliente!: Map<number, DettTrans>;
} 
