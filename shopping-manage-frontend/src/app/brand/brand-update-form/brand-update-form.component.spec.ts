import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandUpdateFormComponent } from './brand-update-form.component';

describe('BrandUpdateFormComponent', () => {
  let component: BrandUpdateFormComponent;
  let fixture: ComponentFixture<BrandUpdateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrandUpdateFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrandUpdateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
