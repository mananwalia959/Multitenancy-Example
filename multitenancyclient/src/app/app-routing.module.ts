import { AddOrEditTodoComponent } from './components/add-or-edit-todo/add-or-edit-todo.component';
import { AuthGuard } from './commons/auth.guard';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { TodoComponent } from './components/todo/todo.component';


const routes: Routes = [
  {path: '', component:TodoComponent, pathMatch: 'full' , canActivate: [AuthGuard]},
  {path: 'login', component:LoginComponent},
  {path: 'todo' , component:AddOrEditTodoComponent,  canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
