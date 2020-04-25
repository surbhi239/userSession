import { RouterModule,Route } from "@angular/router";
import { NgModule } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { LoginComponent } from '../login/login.component';
import { AllUsersComponent } from '../all-users/all-users.component';
import { CheckInCheckOutComponent } from '../check-in-check-out/check-in-check-out.component';

const routes: Route[] = [
    { path: '', redirectTo:'/login', pathMatch: 'full' },
    {path: 'register', component: RegisterComponent},
    {path: 'login', component: LoginComponent},
    {path: 'all-users/:employeeCode', component: AllUsersComponent},
    { path: 'check-in-check-out/:employeeCode', component: CheckInCheckOutComponent}
];

@NgModule({
    imports: [ RouterModule.forChild(routes) ],
    exports: [ RouterModule ],
  })
  export class LayoutRoutingModule {}