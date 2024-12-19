import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl: string = 'http://localhost:8080/api';
  private tokenKey: string = 'jwtToken';
  private _browser!: boolean;
  private httpOptions = {
    headers: new HttpHeaders({

      //Come vengono inviati i dati
      'Content-Type': 'application/json'


    }),
  }

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.getToken() != null);
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient, private _localStorage: LocalStorageService) {
   this.isAuthenticatedSubject.next(this._localStorage.getItem(this.tokenKey) != null);
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, { email, password }, this.httpOptions);
  }

  setToken(token: string): void {
    if(this._localStorage != null){
      this._localStorage.setItem(this.tokenKey, token);
      this.isAuthenticatedSubject.next(true);
    }else
      console.error("errore nel salvare il token")
  }

  getToken(): string | null {
    return this._localStorage ? this._localStorage.getItem(this.tokenKey) : null;
  }

  logout(): void {
    this._localStorage.removeItem(this.tokenKey);
    this.isAuthenticatedSubject.next(false);
    window.location.reload();
  }
}
