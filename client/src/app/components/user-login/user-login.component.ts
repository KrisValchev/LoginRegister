import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginService} from "../../services/login/login.service";
import {NgIf} from "@angular/common";


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
  constructor(private router: Router, private loginService: LoginService) {
  }

  public ngOnInit() {

  }

  public userLogin() {

    if(this.email.value!=null){
      this.userEmail=this.email.value;
    }
    if(this.password.value!=null){
      this.userPassword=this.password.value;
    }
    this.loginService.loginUser(this.userEmail, this.userPassword).subscribe({
      next: data => {
        alert("Successfully logged")
      },
      error: error =>  this.error="Please enter correct email and password"
    });
  }


}

