import { Component,OnInit } from '@angular/core';
//OnInit interface is used to call the ngOnInit method when the component is called
import { RouterOutlet,Router } from '@angular/router';
import {UserLoginComponent} from "./components/user-login/user-login.component";
import{UserRegisterComponent} from "./components/user-register/user-register.component";
import {HttpClientModule} from "@angular/common/http";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, UserLoginComponent,UserRegisterComponent,HttpClientModule],
  //imports all components which is using
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  constructor(private router:Router) {
    //this gives the current route
  }
  public ngOnInit() {
  // this.router.navigate(['login']).catch(console.error);
  //navigate directly to login component when starting the program

  }
}
