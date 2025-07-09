export interface AuthDTO {
  username: string,
  token: string,
  role: UserRole
}

export interface LoginForm {
  email: string,
  password: string
}

export interface SignupForm {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phoneNumber: string;
}

export enum UserRole {
  ADMIN,
  STAFF,
  CUSTOMER
}
