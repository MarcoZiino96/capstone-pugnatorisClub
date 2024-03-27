import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaestriComponent } from './maestri.component';

describe('MaestriComponent', () => {
  let component: MaestriComponent;
  let fixture: ComponentFixture<MaestriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MaestriComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MaestriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
