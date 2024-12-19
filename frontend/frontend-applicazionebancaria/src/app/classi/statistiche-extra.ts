import { DettTrans } from "./dett-trans";

export class StatisticheExtra {
    prestitiRichiestiPerStato!: { [key: string]: number };
	sommaPrestitiApprovatiPerCliente!: { [key: string]: number };
	percentualeTransazioniAccredito!: { [key: string]: number };
	dettagliTransazioniPerCliente!: Map<number, DettTrans>;
} 