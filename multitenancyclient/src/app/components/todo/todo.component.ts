import { Router } from '@angular/router';
import { TodosService } from './../../services/todos.service';
import { Component, OnInit } from '@angular/core';
import { Todo } from 'src/app/models/todo.model';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent implements OnInit {

  constructor(private readonly todoService:TodosService,private readonly router: Router) { }

  todos:Todo[] = [];

  addNewTodo(){
    this.router.navigate(['todo']);
  }

  openTodo(todoId:string){
    this.router.navigate(['todo'], { queryParams: { todo: todoId } });
  }

  ngOnInit() {
    this.todoService.getAllTodos().toPromise().then(todos => this.todos = todos );
  }

}
