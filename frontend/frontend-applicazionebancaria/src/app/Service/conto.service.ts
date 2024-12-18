import { Injectable } from '@angular/core';
import { Conto } from '../Classi/conto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of } from 'rxjs';
import { AuthService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class ContoService {
  private conto!: Conto;
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

  getConti(): Observable<any> {
    return this._http.get(this.baseUrl + '/conti', this.httpOptions).pipe(
      catchError(this.errorHandler<any>("Get Conti"))
    );
  }

  getConto(id: string): Observable<any> {
    return this._http.get(`${this.baseUrl}/conto/${id}`, this.httpOptions).pipe(
      catchError(this.errorHandler<any>("Get Conto by id"))
    );
  }

  deleteConto(id: string): Observable<any> {
    return this._http.delete(`${this.baseUrl}/delete/${id}`, this.httpOptions).pipe(
      catchError(this.errorHandler<any>("Delete Conto"))
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

  setter(conto: Conto) {
    this.conto = conto;
  }

  getter(): Conto{
    return this.conto;
  }
}
