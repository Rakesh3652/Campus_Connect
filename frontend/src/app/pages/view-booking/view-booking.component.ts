import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-view-booking',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './view-booking.component.html',
  styleUrl: './view-booking.component.css'
})
export class ViewBookingComponent {

  constructor(private router: Router) {}

  // Each booking has a ticketId which maps to GET /tickets/{id} on the backend
  bookings: any[] = [
    {
      ticketId: 1,
      title: 'National Hackathon 2024',
      category: 'Tech',
      date: 'Oct 24, 2024',
      venue: 'Auditorium Hall A',
      status: 'confirmed',
      image: 'https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&w=400'
    },
    {
      ticketId: 2,
      title: 'Campus Music Festival',
      category: 'Music',
      date: 'Nov 12, 2024',
      venue: 'Open Grounds',
      status: 'confirmed',
      image: 'https://images.unsplash.com/photo-1514525253344-f814d074e015?auto=format&fit=crop&w=400'
    },
    {
      ticketId: 3,
      title: 'AI & ML Workshop',
      category: 'Workshop',
      date: 'Dec 5, 2024',
      venue: 'Seminar Hall 2',
      status: 'pending',
      image: 'https://images.unsplash.com/photo-1523580494863-6f3031224c94?auto=format&fit=crop&w=400'
    }
  ];

  viewTicket(ticketId: number) {
    this.router.navigate(['/ticket', ticketId]);
  }
}
