import { DOCUMENT } from '@angular/common';
import { Inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  private localStorage: Storage | null;

  constructor(@Inject(DOCUMENT) private document: Document) {
    this.localStorage = this.document.defaultView?.localStorage || null;
  }

  getItem(key: string): string | null {
    console.log(this.localStorage)
    return this.localStorage ? this.localStorage.getItem(key) : null;
  }

  setItem(key: string, value: string): void {
    if (this.localStorage) {
      this.localStorage.setItem(key, value);
    }
  }

  removeItem(key: string): void {
    if (this.localStorage) {
      this.localStorage.removeItem(key);
    }
  }
}
