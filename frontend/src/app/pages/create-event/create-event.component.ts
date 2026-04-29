import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-create-event',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './create-event.component.html',
  styleUrl: './create-event.component.css'
})
export class CreateEventComponent {

  event: any = {
    title: '',
    description: '',
    price: null,
    capacity: null,
    isActive: true,
    startTime: '',
    endTime: '',
    vendor: '',
    category: '',
    venue: '',
    college: ''
  };

  isSubmitting = false;
  submitSuccess = false;
  submitError = '';

  createEvent() {
    if (!this.event.title || !this.event.startTime || !this.event.endTime) {
      this.submitError = 'Please fill in all required fields.';
      return;
    }
    this.submitError = '';
    this.isSubmitting = true;

    console.log('Event payload:', this.event);

    setTimeout(() => {
      this.isSubmitting = false;
      this.submitSuccess = true;
      setTimeout(() => (this.submitSuccess = false), 3000);
    }, 1200);
  }
}
