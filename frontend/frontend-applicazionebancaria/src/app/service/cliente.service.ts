import { Injectable } from '@angular/core';
import { Cliente } from '../classi/cliente';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { catchError, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private cliente!: Cliente;
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

  getClienti(): Observable<any> {
    return this._http.get(this.baseUrl + '/clienti', this.httpOptions).pipe(
      catchError(this.errorHandler<any>("Get Clienti"))
    );
  }

  getCliente(id: number): Observable<any> {
    return this._http.get(`${this.baseUrl}/clienti/${id}`, this.httpOptions).pipe(
      catchError(this.errorHandler<any>("Get Cliente by id"))
    );
  }

  createCliente(cliente: Cliente): Observable<any> {
    return this._http.post(`${this.baseUrl}/clienti`, cliente, this.httpOptions).pipe(
      catchError(this.errorHandler<any>("Create Conto"))
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

  setter(cliente: Cliente) {
    this.cliente = cliente;
  }

  getter(): Cliente{
    return this.cliente;
  }
}
