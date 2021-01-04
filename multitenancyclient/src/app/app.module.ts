import { AuthGuard } from './commons/auth.guard';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeadersComponent } from './components/headers/headers.component';
import { TodoComponent } from './components/todo/todo.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { BasicAuthInterceptor } from './commons/auth-interceptor';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { AddOrEditTodoComponent } from './components/add-or-edit-todo/add-or-edit-todo.component';

function hostFactory() { return window.location.hostname; }

@NgModule({
  declarations: [
    AppComponent,
    HeadersComponent,
    TodoComponent,
    LoginComponent,
    AddOrEditTodoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [
    AuthGuard,
    { provide: 'HOSTNAME', useFactory: hostFactory },
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true } , 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
