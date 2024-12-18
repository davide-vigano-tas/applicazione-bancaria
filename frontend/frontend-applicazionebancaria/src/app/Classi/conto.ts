import { TipoConto } from '../enum/tipo-conto';
import { Cliente } from './cliente';

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

