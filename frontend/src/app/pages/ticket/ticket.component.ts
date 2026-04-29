import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

interface Ticket {
  id: number;
  ticketCode: string;
  qrCode: string;
  bookedAt: string;
  event: {
    id: number;
    name: string;
    description: string;
    venue: string;
    startDate: string;
    category: string;
    imageUrl: string;
  };
  user: { id: number; name: string; email: string; };
  order: { id: number; totalAmount: number; };
}

@Component({
  selector: 'app-ticket',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.css'
})
export class TicketComponent implements OnInit {

  ticket: Ticket | null = null;
  isQrExpanded = false;

  // Mock ticket store — keyed by ticketId.
  // When backend is integrated, replace this lookup with:
  //   this.http.get<Ticket>(`http://localhost:8080/tickets/${id}`)
  private mockTickets: Record<number, Ticket> = {
    1: {
      id: 1,
      ticketCode: 'CC-2024-NAH-00142',
      qrCode: 'https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=CC-2024-NAH-00142',
      bookedAt: '2024-10-10T14:32:00',
      event: {
        id: 1,
        name: 'National Hackathon 2024',
        description: 'Build for the future and win prizes worth ₹5,00,000. 48 hours of high-intensity coding, networking, and fun.',
        venue: 'Auditorium Hall A, Block B',
        startDate: '2024-10-24T09:00:00',
        category: 'Hackathon',
        imageUrl: 'https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&q=80&w=800'
      },
      user: { id: 7, name: 'Rakes ', email: 'rakes@campus.edu' },
      order: { id: 88, totalAmount: 499 }
    },
    2: {
      id: 2,
      ticketCode: 'CC-2024-CMF-00289',
      qrCode: 'https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=CC-2024-CMF-00289',
      bookedAt: '2024-10-18T10:15:00',
      event: {
        id: 2,
        name: 'Campus Music Festival',
        description: 'The biggest open-air music festival on campus. Live bands, food stalls, and a night to remember!',
        venue: 'Open Grounds, North Campus',
        startDate: '2024-11-12T17:00:00',
        category: 'Music',
        imageUrl: 'https://images.unsplash.com/photo-1514525253344-f814d074e015?auto=format&fit=crop&q=80&w=800'
      },
      user: { id: 7, name: 'Rakes', email: 'rakes@campus.edu' },
      order: { id: 91, totalAmount: 299 }
    },
    3: {
      id: 3,
      ticketCode: 'CC-2024-AIW-00374',
      qrCode: 'https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=CC-2024-AIW-00374',
      bookedAt: '2024-11-01T09:00:00',
      event: {
        id: 3,
        name: 'AI & ML Workshop',
        description: 'A hands-on workshop covering the latest in Artificial Intelligence and Machine Learning. Beginner-friendly.',
        venue: 'Seminar Hall 2, CS Block',
        startDate: '2024-12-05T10:00:00',
        category: 'Workshop',
        imageUrl: 'https://images.unsplash.com/photo-1523580494863-6f3031224c94?auto=format&fit=crop&q=80&w=800'
      },
      user: { id: 7, name: 'Rakes', email: 'rakes@campus.edu' },
      order: { id: 95, totalAmount: 199 }
    }
  };

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.ticket = this.mockTickets[id] ?? null;
    });
  }

  formatDate(dateStr: string): string {
    const d = new Date(dateStr);
    return d.toLocaleDateString('en-IN', { weekday: 'short', year: 'numeric', month: 'long', day: 'numeric' });
  }

  formatTime(dateStr: string): string {
    const d = new Date(dateStr);
    return d.toLocaleTimeString('en-IN', { hour: '2-digit', minute: '2-digit' });
  }

  toggleQr() {
    this.isQrExpanded = !this.isQrExpanded;
  }

  downloadTicket() {
    window.print();
  }
}
