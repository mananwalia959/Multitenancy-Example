import { HttpClient } from '@angular/common/http';
import { BaseurlService } from './baseurl.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private readonly baseurlService:BaseurlService ,private readonly client:HttpClient) { }


  authToken =undefined;


  login(username: string, password: string, domain: string) {
    console.log(this.baseurlService.baseurl);
    
    console.log(this.baseurlService.baseurl);
    
    return this.client.post(`${this.baseurlService.baseurl}/auth/login`, {username,password,domain}); 
  }

}
