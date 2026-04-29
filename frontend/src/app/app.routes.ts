import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { EventDetailComponent } from './pages/event-detail/event-detail.component';
import { PaymentComponent } from './pages/payment/payment.component';
import { WishlistComponent } from './pages/wishlist/wishlist.component';
import { AuthComponent } from './pages/auth/auth.component';
import { ViewBookingComponent } from './pages/view-booking/view-booking.component';
import { CreateEventComponent } from './pages/create-event/create-event.component';
import { VendordashboardComponent } from './pages/vendordashboard/vendordashboard.component';
import { TicketComponent } from './pages/ticket/ticket.component';
import { ProfileComponent } from './pages/profile/profile.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'event', component: EventDetailComponent },
  { path: 'payment', component: PaymentComponent },
  { path: 'wishlist', component: WishlistComponent },
  { path: 'auth', component: AuthComponent },
  { path: 'view-booking', component: ViewBookingComponent },
  { path: 'create-event', component: CreateEventComponent },
  { path: 'vendordashboard', component: VendordashboardComponent },
  { path: 'ticket/:id', component: TicketComponent },
  { path: 'profile', component: ProfileComponent },
];
