import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Conciertos } from './conciertos';

describe('Conciertos', () => {
  let component: Conciertos;
  let fixture: ComponentFixture<Conciertos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Conciertos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Conciertos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
