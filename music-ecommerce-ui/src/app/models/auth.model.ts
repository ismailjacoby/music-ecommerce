// Enums
export enum UserRole {
  ADMIN,
  STAFF,
  CUSTOMER
}

// Authentication
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

// Password Management
export interface ForgotPasswordForm {
  email: string
}

export interface ResetPasswordForm {
  token: string,
  password: string
}



