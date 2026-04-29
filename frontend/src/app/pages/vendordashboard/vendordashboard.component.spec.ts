import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendordashboardComponent } from './vendordashboard.component';

describe('VendordashboardComponent', () => {
  let component: VendordashboardComponent;
  let fixture: ComponentFixture<VendordashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VendordashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VendordashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
