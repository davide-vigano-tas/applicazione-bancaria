import { Injectable } from '@angular/core';
import { Statistiche } from '../classi/statistiche';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class StatisticheService {
  private statistiche!: Statistiche;
  private baseUrl: string = 'http://localhost:8080/api';

  private httpOptions = {
    headers: new HttpHeaders({
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET,POST,PUT,DELETE,OPTIONS',
      'Content-Type': 'application/json',
    })
  }

  constructor(private _http: HttpClient, private authService: AuthService) {
    const token = this.authService.getToken();
    if (token) {
      this.httpOptions.headers = this.httpOptions.headers.set('Authorization', `Bearer ${token}`);
    }
  }

  getStatisticheExtra(): Observable<any> {
    return this._http.get(this.baseUrl + '/statistiche-extra', this.httpOptions).pipe(
      catchError(this.errorHandler<any>("Get statistche-extra"))
    );
  }

  getStatistiche(): Observable<any> {
    return this._http.get(`${this.baseUrl}/statistiche`, this.httpOptions).pipe(
      catchError(this.errorHandler<any>("Get Statistiche"))
    );
  }

  // Gestore delle eccezioni
  private errorHandler<T>(operazione = 'Operazione', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operazione} failed: ${error.message}`);
      return of(result as T);
    }
  }

  setter(statistiche: Statistiche) {
    this.statistiche = statistiche;
  }

  getter(): Statistiche{
    return this.statistiche;
  }
}
