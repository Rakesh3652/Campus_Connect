import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent {
  selectedMethod: string = 'card-form';
  isProcessing: boolean = false;

  showForm(formId: string) {
    this.selectedMethod = formId;
  }

  handlePay() {
    this.isProcessing = true;
    setTimeout(() => {
      alert('Payment Successful! Check your email for the ticket.');
      window.location.reload();
    }, 2000);
  }
}
