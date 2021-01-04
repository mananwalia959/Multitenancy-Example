import { TodosService } from './../../services/todos.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-add-or-edit-todo',
  templateUrl: './add-or-edit-todo.component.html',
  styleUrls: ['./add-or-edit-todo.component.css']
})
export class AddOrEditTodoComponent implements OnInit,OnDestroy {

  constructor(private readonly router: Router ,private readonly todosService: TodosService, private route: ActivatedRoute) { }
 

  subscriptions : Subscription[] = [];

  isLoaded = false;

  description:string = '';
  completed:boolean = false;

  todoId : string;
  isUpdateTodo: boolean = false;

  onSave(){
    if(this.isUpdateTodo){
      this.todosService.updateTodo(this.todoId,this.description,this.completed).toPromise().then(res => this.router.navigate(['']))
    }
    else{
      this.todosService.addNewTodo(this.description,this.completed).toPromise().then(res => this.router.navigate(['']));
    }
  }
  onCancel(){
    this.router.navigate(['']);
    
  }
  ngOnInit() {
     this.subscriptions.push(this.route.queryParams.subscribe(res => {
      if(res.todo){
        this.initialiseForExistingTodo(res.todo);
      }
      else{
        this.initialiseForNewTodo();
      }
    }));
    
    
  }
  initialiseForExistingTodo(todo: string) {
    this.todoId = todo
    this.isUpdateTodo = true;
    this.todosService.getTodo(todo).toPromise().then(response =>  {
      const  res = (response as any);
        this.description = res.description;
        this.completed = res.completed;
        this.isLoaded = true;
    })
  }

  initialiseForNewTodo(){
    this.isLoaded = true;

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe())
  }

}
