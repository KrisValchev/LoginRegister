import {Injectable, signal} from '@angular/core';
import {AuthResponse} from "../../models/auth-response.interface";
import {UserResponse} from "../../models/user.interface";
import {HttpClient, HttpHeaders} from "@angular/common/http";


@Injectable({
    providedIn: 'root'
})
export class AuthService {
// signal for telling if user is logged or not
    // undefined -  initial state
    // null - unauthorised
    //AuthResponse - logged in
    currentUserSig = signal<UserResponse | undefined | null>(undefined);


}
