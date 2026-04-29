import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent {

  events = [
    {
      name:'National Hackathon 2024',
      date:'Oct 24, 2024',
      location:'Main Auditorium',
      image:'https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&w=600'
    },
    {
      name:'Campus Music Festival',
      date:'Nov 12, 2024',
      location:'Open Grounds',
      image:'https://images.unsplash.com/photo-1514525253344-f814d074e015?auto=format&fit=crop&w=600'
    }
  ];

  removeEvent(index:number){
    this.events.splice(index,1);
  }
}
