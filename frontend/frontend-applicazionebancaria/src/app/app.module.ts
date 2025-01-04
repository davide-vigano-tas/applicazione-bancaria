import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { LoginFormComponent } from './component/login-form/login-form.component';
import { ClienteFormComponent } from './component/cliente-form/cliente-form.component';
import { StatisticheComponent } from './component/statistiche/statistiche.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { APP_BASE_HREF } from '@angular/common';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { ContoService } from './service/conto.service';
import { ClienteService } from './service/cliente.service';
import { AuthService } from './service/auth.service';
import { AuthGuard } from './classi/auth-guard';
import { ClientiComponent } from './component/clienti/clienti.component';
import { ContiComponent } from './component/conti/conti.component';
import { OrdinaMesiPipe } from './pipes/ordina-mesi.pipe';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: LoginFormComponent },
  { path: 'crea-cliente', component: ClienteFormComponent, canActivate: [AuthGuard] },
  { path: 'statistiche', component: StatisticheComponent, canActivate: [AuthGuard] },
  { path: 'clienti', component:ClientiComponent, canActivate: [AuthGuard]},
  { path: 'conti', component:ContiComponent, canActivate: [AuthGuard]}

]

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    ClienteFormComponent,
    StatisticheComponent,
    ClientiComponent,
    ContiComponent,
    OrdinaMesiPipe,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes, { useHash: true }),
  ],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' },
    provideHttpClient(),
    ContoService,
    ClienteService,
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
