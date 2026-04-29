import { Component, OnInit, OnDestroy, ElementRef, ViewChild } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit, OnDestroy {
  slideIndex = 0;
  totalSlides = 3;
  autoPlayInterval: any;
  isGenreMenuOpen = false;

  @ViewChild('eventRow') eventRow!: ElementRef;

  ngOnInit() {
    this.autoPlayInterval = setInterval(() => {
      this.nextSlide();
    }, 4000);
  }

  ngOnDestroy() {
    if (this.autoPlayInterval) {
      clearInterval(this.autoPlayInterval);
    }
  }

  showSlide(index: number) {
    if (index >= this.totalSlides) this.slideIndex = 0;
    else if (index < 0) this.slideIndex = this.totalSlides - 1;
    else this.slideIndex = index;
  }

  nextSlide() {
    this.showSlide(this.slideIndex + 1);
  }

  prevSlide() {
    this.showSlide(this.slideIndex - 1);
  }

  toggleGenre() {
    this.isGenreMenuOpen = !this.isGenreMenuOpen;
  }

  scrollLeft() {
    if (this.eventRow) {
      this.eventRow.nativeElement.scrollBy({ left: -300, behavior: 'smooth' });
    }
  }

  scrollRight() {
    if (this.eventRow) {
      this.eventRow.nativeElement.scrollBy({ left: 300, behavior: 'smooth' });
    }
  }
}
