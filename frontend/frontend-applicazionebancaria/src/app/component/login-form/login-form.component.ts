import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  loginForm: FormGroup;
  loading = false;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]]
    });
  }

  // Metodo per il login
  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authService.login(this.loginForm.get("email")?.value, this.loginForm.get("password")?.value).pipe(
      first() // Ottieni solo la prima risposta
    ).subscribe({
      next: (data) => {
        this.router.navigate(['/statistiche']); // Reindirizza alla pagina delle statistiche
      },
      error: (error) => {
        this.error = 'Credenziali errate o problema con il server.';
        this.loading = false;
      }
    });
  }
}
