import { Component, OnInit } from '@angular/core';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  isAuthenticated: boolean = false;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Controlla se il token è presente al caricamento della pagina
    this.isAuthenticated = !!this.authService.getToken();
  }

  // Metodo per il logout
  logout(): void {
    this.authService.logout();
    this.isAuthenticated = false;  // Aggiorna lo stato di autenticazione
  }
}
