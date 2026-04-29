import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-event-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './event-detail.component.html',
  styleUrl: './event-detail.component.css'
})
export class EventDetailComponent {
  count = 1;
  basePrice = 499;

  get totalPrice(): number {
    return this.basePrice * this.count;
  }

  increment() {
    this.count++;
  }

  decrement() {
    if (this.count > 1) {
      this.count--;
    }
  }

  updateBasePrice(event: any) {
    this.basePrice = parseInt(event.target.value, 10);
  }
}
