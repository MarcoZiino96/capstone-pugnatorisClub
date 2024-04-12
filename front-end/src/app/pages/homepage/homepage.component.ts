import { Component } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss'
})
export class HomepageComponent {
  images = [
    "../../../../assets/img/boxe.jpeg",
    "../../../../assets/img/mma-grappling.jpeg",
    "../../../../assets/img/muay-thay.jpeg"
  ];
}
