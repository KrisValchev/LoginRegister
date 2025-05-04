import { Injectable } from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {RegisterRequest} from "../../models/register-request.interface";
import {AuthResponse} from "../../models/auth-response.interface";
import {Observable} from "rxjs";


// service used to transfer and take data from the server
@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  baseUrl:string="http://localhost:8080/api";
  constructor(private http:HttpClient) {
//to use httpClient you should insert provideHttpClient() into the providers of the application(app.config.ts)
  }

   register(user:RegisterRequest){
    //http post which gets RegisterRequest and returns AuthResponse with token
    return this.http.post(this.baseUrl+"/users/register",user);

  }

}
