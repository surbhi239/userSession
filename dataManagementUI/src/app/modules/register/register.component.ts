import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../shared/user.service';
import { Subject } from 'rxjs';
import { User } from '../shared/User';
import { takeUntil } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  user: User;
  private ngUnsubscribe: Subject<void> = new Subject<void>();
  constructor(private formBuilder: FormBuilder, private userService: UserService, private route: Router) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm(){
    this.registerForm = this.formBuilder.group({
      employeeCode: [''],
      name: [''],
      dateOfBirth: [''],
      password: ['', Validators.required]
    })
  }

  onSubmit(registerUser){
    this.userService.registerUser(registerUser.value).pipe(takeUntil(this.ngUnsubscribe)).subscribe(res =>{
    this.route.navigate(['/login']);
    })
  }

}
