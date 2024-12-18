import { Component } from '@angular/core';
import { Conto } from '../../classi/conto';
import { ContoService } from '../../service/conto.service';
import { Router } from 'express';

@Component({
  selector: 'app-conti',
  templateUrl: './conti.component.html',
  styleUrl: './conti.component.css'
})
export class ContiComponent {
  conti!: Conto[];

  constructor(private _contoService: ContoService, private _router: Router){ }

  ngOnInit(): void {
    this._contoService.getConti().subscribe({
      next: (conti) => this.conti = conti,
      error: (e) => console.error(e),
      complete: () => console.log("Caricamento dei conti completato"),
    })
  }

  deleteConto(conto: Conto){
    this._contoService.deleteConto(conto.codConto).subscribe(() => {
      this.conti.splice(this.conti.indexOf(conto), 1);
    })
  }
}
