import {Component, inject, isStandalone} from '@angular/core';
import {OnInit} from "@angular/core";
import {Router, RouterLink} from "@angular/router";
import {FormGroup, FormControl,  ReactiveFormsModule, FormsModule} from "@angular/forms";
import {RegisterService} from "../../services/register/register.service";
import {NgIf} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {RegisterRequest} from "../../models/register-request.interface";

//FormModule class for communication between html and ts of the component through variables


@Component({
  selector: 'user-register',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    ReactiveFormsModule,
    NgIf,
  ],
  templateUrl: './user-register.component.html',
  styleUrl: './user-register.component.css'
})
export class UserRegisterComponent implements OnInit {
  user: RegisterRequest={
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    address: ""
  };
  error: string = "";
  email = new FormControl("")
  address = new FormControl("")
  password = new FormControl('')
  passwordConfirm = new FormControl('')
  firstName = new FormControl('');
  lastName = new FormControl('');

  registerFormGroup: FormGroup = new FormGroup({
    firstName: this.firstName,
    lastName: this.lastName,
    address: this.address,
    email: this.email,
    password: this.password,
    passwordConfirm: this.passwordConfirm
  });

  http= inject(HttpClient);

  constructor(private registerService: RegisterService, private router: Router) {

  }

  ngOnInit() {
  }

  userRegister() {
    if (this.firstName.value !== null) {
      this.user.firstName = this.firstName.value;
    }
    if (this.lastName.value !== null) {
      this.user.lastName = this.lastName.value;
    }
    if (this.address.value !== null) {
      this.user.address = this.address.value;
    }
    if (this.password.value !== null) {
      this.user.password = this.password.value;
    }
    if (this.email.value !== null) {
      this.user.email = this.email.value;
    }
    if (this.passwordConfirm.value == this.password.value) {

      this.registerService.register(this.user).subscribe({
        next: data => {
          alert("Successfully User is registered")
          this.router.navigate(['login']);
        },
        error: error => this.error = "User with this email already exists"
      });
    } else {
      this.error = "Confirm password does not match password";
    }
  }

}
