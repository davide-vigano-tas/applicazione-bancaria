import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClienteComponent } from './cliente/cliente.component';
import { LoginResponseComponent } from './login-response/login-response.component';
import { ContoComponent } from './conto/conto.component';
import { StatisticheComponent } from './statistiche/statistiche.component';
import { StatisticheExtraComponent } from './statistiche-extra/statistiche-extra.component';

@NgModule({
  declarations: [
    AppComponent,
    ClienteComponent,
    LoginResponseComponent,
    ContoComponent,
    StatisticheComponent,
    StatisticheExtraComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
