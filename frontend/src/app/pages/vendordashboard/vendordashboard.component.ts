import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-vendordashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './vendordashboard.component.html',
  styleUrl: './vendordashboard.component.css'
})
export class VendordashboardComponent {
  
  constructor(private router: Router) {}

  totalEarnings = 45000;
  totalTicketSale = 120;
  totalTax = 2000;
  capacity = 500;
  netEarnings = 43000;
  totalTransactions = 150;

  myEvents: any[] = [
    { eventId: 1, title: 'AI Workshop', category: 'Workshop', rating: '4.8', image: 'https://images.unsplash.com/photo-1523580494863-6f3031224c94?auto=format&fit=crop&w=200' },
    { eventId: 2, title: 'Hackathon', category: 'Competition', rating: '4.9', image: 'https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&w=200' }
  ];

  viewEventDashboard(eventId: number) {
    this.router.navigate(['/event-dashboard', eventId]);
  }
}
