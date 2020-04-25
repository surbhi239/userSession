export interface User{
    employeeCode: number;
    name: String;
    dateOfBirth: Date;
    password: String;
    loginLogoutList: LoginLogoutList;
}

export class LoginLogoutList{
    checkInDate: Date = new Date();
    checkOutDate: Date= new Date();
}