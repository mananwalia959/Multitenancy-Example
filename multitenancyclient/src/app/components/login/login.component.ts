import { Router } from '@angular/router';
import { Component, Inject, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username:string = '';
  password:string = '';
  message = '';
  constructor(@Inject('HOSTNAME') private host: string 
  , private readonly authservice:AuthService,private readonly router: Router ) { }


  onLogin(){ 
    this.message = '';

    this.authservice.login(this.username,this.password,this.host).toPromise()
      .then(res => {
        this.authservice.authToken = (res as any).token;
        this.router.navigate(['']);
      } )
      .catch(err => this.message = "invalid username or password");
  }

  ngOnInit() {
  }

}
