import { Injectable } from '@angular/core';
import {HttpClient,HttpClientModule} from "@angular/common/http";
import {LoginRequest} from "../../models/login-request.interface";
import {AuthResponse} from "../../models/auth-response.interface";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
baseUrl:string="http://localhost:8080/api/users/login";
  constructor(private httpClient:HttpClient) { }

  public loginUser(user:LoginRequest){
    return this.httpClient.post<AuthResponse>(this.baseUrl,user);
  }
}
