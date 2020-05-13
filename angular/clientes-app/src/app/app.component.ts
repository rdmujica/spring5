import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Bienvenido a Angular';
  curso: string = 'Curos Spring con Angular 9';
  profesor: string = 'Andrés Guzmán';
  alumno: string = 'Rafael Mujica'
}
