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
  loading: boolean = false;
  error: string | null = null;
  token!: string;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]],
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.error = null;

    const { email, password } = this.loginForm.value;

    this.authService.login(email, password).subscribe({
      next: (response) => {
        this.loading = false;
        // Salvare il token nel localStorage
        if (response && response.token) {
          this.token = response.token;
          this.authService.setToken(this.token);
          this.router.navigate(['/statistiche']);
        }
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Credenziali non valide. Per favore riprova.';
        console.error('Login failed', err);
      }
    });
  }
}

