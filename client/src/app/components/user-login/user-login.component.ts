import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginService} from "../../services/login/login.service";
import {NgIf} from "@angular/common";
import {LoginRequest} from "../../models/login-request.interface";
import {AuthService} from "../../services/auth/auth.service";


@Component({
  selector: 'user-login',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule,
    FormsModule,
    NgIf
  ],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent implements OnInit {
  email =new FormControl("");
  password= new FormControl('');
  loginGroup: FormGroup = new FormGroup({
    email: this.email,
    password: this.password,
  });
  error:string="";
  userEmail:string="";
  userPassword:string="";
  user:LoginRequest={
    email:"",
    password:""
  };
  constructor(private router: Router, private loginService: LoginService, private authService: AuthService) {
  }

  public ngOnInit() {

  }

  public userLogin() {

    if(this.email.value!=null){
      this.user.email=this.email.value;
    }
    if(this.password.value!=null){
      this.user.password=this.password.value;
    }

    this.loginService.loginUser(this.user).subscribe({
      next: data => {
        alert("Successfully logged")
        //save token in localStorage
        localStorage.setItem('token',data.token)
        //set signal to data.user
        this.authService.currentUserSig.set(data.user);
        this.router.navigate(['home']);

      },
      error: error =>  this.error="Please enter correct email and password"
    });
  }


}

