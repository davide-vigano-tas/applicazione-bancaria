import { DettTrans } from "./dett-trans";

export class StatisticheExtra {
    prestitiRichiestiPerStato!: { [key: string]: number };
	sommaPrestitiApprovatiPerCliente!: { [key: string]: number };
	percentualeTransazioniAccredito!: number;
	dettagliTransazioniPerCliente!: Map<number, DettTrans>;
} 