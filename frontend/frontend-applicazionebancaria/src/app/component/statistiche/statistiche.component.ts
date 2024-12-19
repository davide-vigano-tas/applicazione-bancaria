import { Component } from '@angular/core';
import { Statistiche } from '../../classi/statistiche';
import { StatisticheService } from '../../service/statistiche.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-statistiche',
  templateUrl: './statistiche.component.html',
  styleUrl: './statistiche.component.css'
})
export class StatisticheComponent {

  statistiche!: Statistiche;
    
      
      constructor(private _statService: StatisticheService, private _router: Router) {
    
      }
      ngOnInit(): void {
        this._statService.getStatistiche().subscribe(
          { //Operazione da eseguire subito dopo
            next: (statistiche) => this.statistiche = statistiche,
            error: (e) => console.error(e),
            complete: () => console.info('getStatiche chiamato')
          }
        );
      }

}
