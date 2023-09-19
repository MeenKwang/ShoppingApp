import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryCreateFormComponent } from './category-create-form.component';

describe('CategoryCreateFormComponent', () => {
  let component: CategoryCreateFormComponent;
  let fixture: ComponentFixture<CategoryCreateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoryCreateFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryCreateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
