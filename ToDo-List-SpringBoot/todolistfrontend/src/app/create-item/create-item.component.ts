import { Component, OnInit } from '@angular/core';
import { Item } from '../item';

@Component({
  selector: 'app-create-item',
  templateUrl: './create-item.component.html',
  styleUrl: './create-item.component.css'
})
export class CreateItemComponent implements OnInit {
  item: Item = new Item();

  constructor() {}
  ngOnInit(): void {
      
  }

  onSubmit() {
    
  }
}
