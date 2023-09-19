import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-notify-modal',
  templateUrl: './notify-modal.component.html',
  styleUrls: ['./notify-modal.component.css']
})
export class NotifyModalComponent implements OnInit {

  @Input() message : any;

  constructor(    
    public activeModal: NgbActiveModal,
  ) {}

  ngOnInit(): void {
  }

}
