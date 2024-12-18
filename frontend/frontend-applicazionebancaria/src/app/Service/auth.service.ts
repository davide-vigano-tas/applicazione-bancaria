import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl: string = 'http://localhost:8080/api';
  private tokenKey: string = 'jwtToken';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    const value = this.http.post(`${this.baseUrl}/login`, { email, password });
    console.log(value)
    return value
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }
}
