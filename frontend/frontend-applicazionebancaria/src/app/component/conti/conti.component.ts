import { Component } from '@angular/core';
import { Conto } from '../../classi/conto';
import { ContoService } from '../../service/conto.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-conti',
  templateUrl: './conti.component.html',
  styleUrl: './conti.component.css'
})
export class ContiComponent {

    conti!: Conto[];
  
    
    constructor(private _contiService: ContoService, private _router: Router) {
  
    }
    ngOnInit(): void {
      this._contiService.getConti().subscribe(
        { //Operazione da eseguire subito dopo
          next: (conti) => this.conti = conti,
          error: (e) => console.error(e),
          complete: () => console.info('getConti chiamato correttamente')
        }
      );
    }

    deleteConto(c: Conto) {

    }
}
