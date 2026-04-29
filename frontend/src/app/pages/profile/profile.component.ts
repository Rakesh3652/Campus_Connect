import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user = {
    firstName: 'Amaan',
    lastName: 'Shaikh',
    email: 'amaan@campus.edu',
    phone: '+91 9876543210',
    role: 'Student',
    pfpUrl: 'https://i.pravatar.cc/150?img=12'
  };

  isEditing = false;
  saveSuccess = false;
  activeTab = 'account';

  setTab(tab: string) {
    this.activeTab = tab;
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      // Create a local URL for the selected image to preview it
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.user.pfpUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  saveChanges() {
    this.isEditing = false;
    this.saveSuccess = true;
    
    // Simulate API call saving data
    setTimeout(() => {
      this.saveSuccess = false;
    }, 3000);
  }
}
