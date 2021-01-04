import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-headers',
  templateUrl: './headers.component.html',
  styleUrls: ['./headers.component.css']
})
export class HeadersComponent implements OnInit {

  constructor(private readonly authService:AuthService,private readonly router: Router) { }

   onLogout() {
     this.authService.authToken = undefined;
     this.router.navigate(['login']);
   }


  ngOnInit() {
  }

}
