import { Component, OnInit } from '@angular/core';
import { UserService } from '../shared/user.service';
import { takeUntil } from 'rxjs/operators';
import { User, LoginLogoutList } from '../shared/User';
import { Subject } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-check-in-check-out',
  templateUrl: './check-in-check-out.component.html',
  styleUrls: ['./check-in-check-out.component.css']
})
export class CheckInCheckOutComponent implements OnInit {

  sessions: LoginLogoutList[]= [];
  employeeCode:  number;
  public router;
  jsonTitle = [ 
    'checkInDate', 'checkOutDate'
  ];
  private ngUnsubscribe: Subject<void> = new Subject<void>();
  constructor(private userService: UserService, private activatedRoute: ActivatedRoute, private route: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params =>{
      this.employeeCode = +params['employeeCode'];
      console.log(this.employeeCode)
    })
    this.userService.getAllSessions(this.employeeCode).pipe(takeUntil(this.ngUnsubscribe)).subscribe((res: LoginLogoutList[]) =>{
      console.log(res)
      this.sessions.push(...res);
    })
    
    
  }

  download(){
    this.userService.downloadFile(this.sessions, 'jsontocsv', this.jsonTitle);
  }

  back(){
    this.router = `/all-users/${this.employeeCode}`
    this.route.navigate([this.router]); 
  }

}
