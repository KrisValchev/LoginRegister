import { Injectable } from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {User} from "../../models/User";

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
   register(user:User){
    return this.http.post(this.baseUrl+"/users/register",user);
  }

}
