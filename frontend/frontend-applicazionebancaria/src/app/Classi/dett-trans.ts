import { TipoTransazione } from './tipo-transazione';

export class DettTrans {
    importoMedio!: number;
	numeroTotale!: number;
	tipi!: Map<TipoTransazione, number>;
}
