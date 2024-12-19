import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../classi/cliente';
import { ClienteService } from '../../service/cliente.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-clienti',
  templateUrl: './clienti.component.html',
  styleUrl: './clienti.component.css'
})
export class ClientiComponent implements OnInit{

  clienti!: Cliente[];
  cliente!: Cliente;

  
  constructor(private _clienteService: ClienteService, private _router: Router) {
      
  }
  ngOnInit(): void {
    this._clienteService.getClienti().subscribe(
      {
        next: (clienti) => this.clienti = clienti,
        error: (e) => console.error(e),
        complete: () => console.info('getClienti chiamato correttamente')
      }
    );
  }

  getCliente(c: Cliente): void {
    this._clienteService.getCliente(c.codCliente).subscribe(
      {
        next: (cliente) => this.cliente = cliente,
        error: (e) => console.log(e),
        complete: () => console.log('getCliente chiamato correttamente')
      }
    );
  }

}
