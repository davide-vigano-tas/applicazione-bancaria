import { Cliente } from './cliente';

export enum TipoConto {
    CORRENTE = 'CORRENTE',
    RISPARMIO = 'RISPARMIO',
}

export class Conto {
  codConto!: number;
  tipoConto!: TipoConto;
  saldo: number = 0.0;
  cliente!: Cliente;

  constructor(data?: Partial<Conto>) {
    if (data) {
      Object.assign(this, data);
    }
  }
}

