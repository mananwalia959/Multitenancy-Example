import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BaseurlService {

  baseurl = 'http://localhost:8080';

  constructor() { }
}
