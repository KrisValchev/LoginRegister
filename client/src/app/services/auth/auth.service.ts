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

     isTokenExpired(token: string): boolean {
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const expiry = payload.exp;
            const now = Math.floor(Date.now() / 1000); // current time in seconds
            return now >= expiry;
        } catch (e) {
            return true; // if there's any error, treat as expired
        }
    }
}
