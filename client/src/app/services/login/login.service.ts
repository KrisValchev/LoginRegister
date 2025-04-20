import { Injectable } from '@angular/core';
import {HttpClient,HttpClientModule} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
baseUrl:string="http://localhost:8080/api/users/login";
  constructor(private httpClient:HttpClient) { }

  public loginUser(email:string,password:string){
    return this.httpClient.post(this.baseUrl,{email,password});
  }
}
