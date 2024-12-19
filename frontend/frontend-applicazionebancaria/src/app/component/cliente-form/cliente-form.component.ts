import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NOMEM } from 'dns';
import { ClienteService } from '../../service/cliente.service';
import { Cliente } from '../../classi/cliente';

@Component({
  selector: 'app-cliente-form',
  templateUrl: './cliente-form.component.html',
  styleUrl: './cliente-form.component.css'
})
export class ClienteFormComponent {
  createClienteForm: FormGroup;
  loading: boolean = false;
  error: string | null = null;
  token!: string;

  constructor(private fb: FormBuilder, private clienteService: ClienteService, private router: Router) {
    this.createClienteForm = this.fb.group({
      nome: ['', [Validators.required, Validators.pattern("^[a-zA-Z ,.'-]{2,30}$")]],
      cognome: ['', [Validators.required, Validators.pattern("^[a-zA-Z ,.'-]{2,30}$")]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  onSubmit(): void {
    if (this.createClienteForm.invalid) {
      return;
    }

    this.loading = true;
    this.error = null;

    const { nome, cognome, email, password } = this.createClienteForm.value;
    const cliente = new Cliente();
    cliente.nomeCliente = nome;
    cliente.cognomeCliente = cognome;
    cliente.emailCliente = email;
    cliente.passwordCliente = password;

    this.clienteService.createCliente(cliente).subscribe({
      next: (response) => {
        this.loading = false;
        this.router.navigate(['/statistiche']);
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Creazione fallita. Per favore riprova.';
        console.error('Create failed', err);
      }
    });
  }

}
