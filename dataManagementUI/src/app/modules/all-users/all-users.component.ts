import { Component, OnInit, OnDestroy } from '@angular/core';
import { User } from '../shared/User';
import { UserService } from '../shared/user.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit , OnDestroy{
  users: User[]= [];
  employeeCode:  number;
  public router;
  jsonTitle = [ 
    'employeeCode', 'name', 'dateOfBirth'
  ];
  private ngUnsubscribe: Subject<void> = new Subject<void>();
  constructor(private userService: UserService, private route: Router, private activatedRoute: ActivatedRoute,
    private location: Location) { }

  ngOnInit(): void {
    this.userService.getAllUser().pipe(takeUntil(this.ngUnsubscribe)).subscribe((res: User[]) =>{
      this.users.push(...res);
      })
      this.activatedRoute.params.subscribe(params =>{
        this.employeeCode = +params['employeeCode'];
        console.log(this.employeeCode)
      })
  }
  ngOnDestroy(){
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  logOut(empCode){
    this.userService.logoutUser(empCode).pipe(takeUntil(this.ngUnsubscribe)).subscribe(res =>{
      console.log(res)
      this.route.navigate(['login']);
    }
    )
  }
  checkInCheckOut(empCode: number){
    this.router = `/check-in-check-out/${empCode}`
    this.route.navigate([this.router]);
  }

  download(){
    this.userService.downloadFile(this.users, 'jsontocsv', this.jsonTitle);
  }

  deleteUsers(empCode: number){
    console.log(this.employeeCode)
    this.userService.deleteUser(empCode).pipe(takeUntil(this.ngUnsubscribe)).subscribe(res=>{
      console.log(this.employeeCode)
      if(empCode == this.employeeCode)
        this.route.navigate(['/login']);
        else
        {
        this.router = `/all-users/${this.employeeCode}`
        this.route.navigate([this.router]);
        location.reload();}
    })

  }
}
