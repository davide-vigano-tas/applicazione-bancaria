import { Component, OnInit } from '@angular/core';
import { Statistiche } from '../../classi/statistiche';
import { StatisticheService } from '../../service/statistiche.service';
import { Router } from '@angular/router';
import { StatisticheExtra } from '../../classi/statistiche-extra';

@Component({
  selector: 'app-statistiche',
  templateUrl: './statistiche.component.html',
  styleUrls: ['./statistiche.component.css']
})
export class StatisticheComponent implements OnInit {

  statistiche!: Statistiche;
  statisticheExtra!: StatisticheExtra;
    
  constructor(private _statService: StatisticheService, private _router: Router) { }

  ngOnInit(): void {
    this._statService.getStatistiche().subscribe({
      next: (statistiche) => this.statistiche = statistiche,
      error: (e) => console.error(e),
      complete: () => console.info(this.statistiche)
    });

    this._statService.getStatisticheExtra().subscribe({
      next: (statisticheExtra) => this.statisticheExtra = statisticheExtra,
      error: (e) => console.error(e),
      complete: () => console.info(this.statisticheExtra)
    });

  }
}
