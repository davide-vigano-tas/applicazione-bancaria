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
        {
          next: (conti) => this.conti = conti,
          error: (e) => console.error(e),
          complete: () => console.info('getConti chiamato correttamente')
        }
      );
    }

    deleteConto(conto: Conto){
      console.log("Conto: ",conto)
      this._contiService.deleteConto(conto.codConto).subscribe(() => {
        this.conti.splice(this.conti.indexOf(conto), 1);
      })
    }
}
