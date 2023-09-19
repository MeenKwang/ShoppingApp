import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandCreateFormComponent } from './brand-create-form.component';

describe('BrandCreateFormComponent', () => {
  let component: BrandCreateFormComponent;
  let fixture: ComponentFixture<BrandCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrandCreateFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrandCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
