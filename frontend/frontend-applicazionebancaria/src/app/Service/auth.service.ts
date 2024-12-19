import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

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
      'Content-Type' : 'application/json'


    }),
  }

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.getToken() !== null);
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: Object) {
    this._browser = isPlatformBrowser(platformId);
  }

  login(email: string, password: string): Observable<any> {
    const value = this.http.post(`${this.baseUrl}/login`, { email, password }, this.httpOptions);
    console.log(value)  
    return value
  }

  setToken(token: string): void {
    if(this._browser) {
      localStorage.setItem(this.tokenKey, token);
      this.isAuthenticatedSubject.next(true);
    }
      
  }

  getToken(): string | null {
    if(this._browser)
      return localStorage.getItem(this.tokenKey);
    return null;
  }

  logout(): void {
    if(this._browser) {
      localStorage.removeItem(this.tokenKey);
      this.isAuthenticatedSubject.next(false);
      window.location.reload();
    }
      
  }
}
