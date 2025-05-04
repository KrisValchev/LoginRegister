import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";
import {NgIf} from "@angular/common";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {getXHRResponse} from "rxjs/internal/ajax/getXHRResponse";
import {AuthResponse} from "../../models/auth-response.interface";
import {UserResponse} from "../../models/user.interface";

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {

  constructor(protected authService: AuthService, private http: HttpClient) {
  }

  ngOnInit() {
    const token = localStorage.getItem('token'); // or from a service holding the token
    if (token) {
      if(!this.authService.isTokenExpired(token)) {
        this.http.get<UserResponse>("http://localhost:8080/api/users/loggedUser", {
          headers: new HttpHeaders({
            'Authorization': `Bearer ${token}`
          })
        }).subscribe({
          next: (response) => {
            this.authService.currentUserSig.set(response);
          },
          error: (err) => {
            console.error('Error fetching logged user:', err);

          }
        });
      }
      else{
        localStorage.removeItem('token');
      }
    }
    else{
      this.authService.currentUserSig.set(null);
    }
  }

  logout() {
    //when logging out setting signal to null and clearing local storage token
localStorage.removeItem('token');
this.authService.currentUserSig.set(null);
  }
}
