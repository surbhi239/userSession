import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  springEndPoint:string;

  constructor(private http:HttpClient) { 

    this.springEndPoint="http://localhost:8080/user";
   
  }

  registerUser(newUser){
    const url=this.springEndPoint+"/register";
    return this.http.post(url,newUser,{
      responseType:'json'
    });
  }
  loginUser(user){
    const url=this.springEndPoint+"/login";
    return this.http.post(url,user,{
      responseType:'json'
    });
  }
  logoutUser(employeeCode){
    const url=`${this.springEndPoint}/logout?employeeCode=${employeeCode}`;
    return this.http.put(url,employeeCode);
  }
  getAllUser(){
    const url= this.springEndPoint+"/allUsers";
    return this.http.get(url);
  }
  deleteUser(employeeCode: number): Observable<any>{
  const url = `${this.springEndPoint}/deleteUser/${employeeCode}`;
  console.log(url)
  return this.http.delete(url, { responseType: 'text' });

}
getUser(employeeCode){
  const url = `${this.springEndPoint}/getUser?employeeCode=${employeeCode}`;
  return this.http.get(url,{responseType:'json'});
}
updateUser(newUser){
  const url=this.springEndPoint+"/updateUser";
  return this.http.post(url,newUser,{
    responseType:'json'
  });
}
getAllSessions(employeeCode){
  const url= `${this.springEndPoint}/allCheckInCheckOut?employeeCode=${employeeCode}`;
  return this.http.get(url);
}
downloadFile(data, filename = 'data', jsonTitle) {
		let csvData = this.ConvertToCSV(data, jsonTitle); 
		console.log(csvData) 
		let blob = new Blob(['\ufeff' + csvData], { 
			type: 'text/csv;charset=utf-8;'
		}); 
		let dwldLink = document.createElement("a"); 
		let url = URL.createObjectURL(blob); 
		let isSafariBrowser = navigator.userAgent.indexOf('Safari') != -1 &&
		navigator.userAgent.indexOf('Chrome') == -1; 

		if (isSafariBrowser) {
			dwldLink.setAttribute("target", "_blank"); 
		} 
		dwldLink.setAttribute("href", url); 
		dwldLink.setAttribute("download", filename + ".csv"); 
		dwldLink.style.visibility = "hidden"; 
		document.body.appendChild(dwldLink); 
		dwldLink.click(); 
		document.body.removeChild(dwldLink); 
	} 
	ConvertToCSV(objArray, headerList) { 
		let array = 
			typeof objArray != 'object' ? JSON.parse(objArray) : objArray; 
		let str = ''; 
		let row = 'S.No, '; 
		for (let index in headerList) { 
			row += headerList[index] + ', '; 
		} 
		row = row.slice(0, -1); 
		str += row + '\r\n'; 
		for (let i = 0; i < array.length; i++) { 
			let line = (i + 1) + ''; 
			for (let index in headerList) { 
				let head = headerList[index]; 
				line += ', ' + array[i][head]; 
			} 
			str += line + '\r\n'; 
		} 
		return str; 
	}
 
}