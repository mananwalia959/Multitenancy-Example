import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Todo } from '../models/todo.model';
import { BaseurlService } from './baseurl.service';

@Injectable({
  providedIn: 'root'
})
export class TodosService {

  constructor(private readonly client : HttpClient , private readonly baseurlService:BaseurlService ) { }

  
  public  getAllTodos() : any {
    return this.client.get(`${this.baseurlService.baseurl}/api/todos`); 
  }

  public  getTodo(todoId:string)  {
    return this.client.get(`${this.baseurlService.baseurl}/api/todos/${todoId}`); 
  }

  public  addNewTodo(description:string, completed:boolean)  {
    return this.client.post(`${this.baseurlService.baseurl}/api/todos`, {description,completed}); 
  }

  public updateTodo(todoId: string, description: string, completed: boolean) {
    return this.client.patch(`${this.baseurlService.baseurl}/api/todos/${todoId}`, {description,completed});
  }
  
}
