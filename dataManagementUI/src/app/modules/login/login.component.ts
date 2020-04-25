import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../shared/user.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm: FormGroup;
  login: boolean;
  public router;
  private ngUnsubscribe: Subject<void> = new Subject<void>();
  constructor(private formBuilder: FormBuilder, private route: Router, private userService: UserService) {
    this.createForm();
   }

  ngOnInit(): void {
  }
  createForm(){
    this.loginForm = this.formBuilder.group({
      employeeCode: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  ngOnDestroy(){
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
  onSubmit(loginUser){
    this.userService.loginUser(loginUser.value).pipe(takeUntil(this.ngUnsubscribe)).subscribe(res =>{
    this.router = `/all-users/${loginUser.value.employeeCode}`
    this.route.navigate([this.router]);
    })
}
}
