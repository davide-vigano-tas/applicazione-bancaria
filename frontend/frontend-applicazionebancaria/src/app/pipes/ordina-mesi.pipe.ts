import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ordinaMesi'
})
export class OrdinaMesiPipe implements PipeTransform {

  transform(mappa: Map<string, number> | { [key: string]: number } | null): { key: string, value: number }[] {
    if (!mappa) return [];

    // Ordine corretto dei mesi
    const ordineMesi = [
      'Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno',
      'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'
    ];

    // Converti l'input in un array ordinato
    const entries = mappa instanceof Map ? Array.from(mappa.entries()) : Object.entries(mappa);
    return entries
      .sort((a, b) => ordineMesi.indexOf(a[0]) - ordineMesi.indexOf(b[0]))
      .map(([key, value]) => ({ key, value }));
  }

}
