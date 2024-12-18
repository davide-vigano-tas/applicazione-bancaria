export class Cliente {
    codCliente!: number;
    nomeCliente!: string;
    cognomeCliente!: string;
    emailCliente!: string;
    passwordCliente!: string;
    tentativiErrati: number = 0;
    accountBloccato: boolean = false;
    saldoConto: number = 0.00;

    constructor(data?: Partial<Cliente>) {
        if (data) {
            Object.assign(this, data);
        }
    }
}