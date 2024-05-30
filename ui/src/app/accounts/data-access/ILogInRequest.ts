export interface ILogInRequest {
  subject: string,
  login: string,
  password: string
}

export enum Subject {
  EMAIL_ADDRESS,
  PHONE_NUMBER
}