import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { LayoutRoutingModule } from './layout-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from '../login/login.component';
import { AllUsersComponent } from '../all-users/all-users.component';
import { HttpClientModule } from '@angular/common/http';
import { CheckInCheckOutComponent } from '../check-in-check-out/check-in-check-out.component';


@NgModule({
  declarations: [
  RegisterComponent,
  LoginComponent,
  AllUsersComponent,
  CheckInCheckOutComponent],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    LayoutRoutingModule,
    HttpClientModule
  ],
  providers: []
})
export class LayoutModule { }
