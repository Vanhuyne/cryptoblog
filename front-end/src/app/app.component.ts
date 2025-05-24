import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'front-end';

  menuItems = [ 
    {
        label: 'Home',
        routerLink: ['/']
      },
      {
        label: 'Blog',
        routerLink: ['/blog']
      },
      {
        label: 'Service',
        items: [
          {
            label: 'Web Development',
            routerLink: ['/services/web-development']
          },
          {
            label: 'Mobile Apps',
            routerLink: ['/services/mobile-apps']
          },
          {
            label: 'Consulting',
            routerLink: ['/services/consulting']
          },
          {
            label: 'Support',
            routerLink: ['/services/support']
          }
        ]
      },
      {
        label: 'About',
        routerLink: ['/about']
      },
      {
        label: 'Contact',
        routerLink: ['/contact']
      }
  ];
  constructor() { 
    // Initialize any necessary data or services here

  }
}
