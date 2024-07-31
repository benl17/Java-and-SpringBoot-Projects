import { Component, OnInit } from '@angular/core';
import { Item } from '../item';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrl: './item-list.component.css'
})
export class ItemListComponent implements OnInit {
  items!: Item[];

  ngOnInit(): void {
      this.items = [{
        "itemID": 1, 
        "itemName": "Move out of Equinox", 
        "dueDate": "08/13/24",
        "itemImportance": 3
      },
      {
        "itemID": 2, 
        "itemName": "Apply to Cox Automotive", 
        "dueDate": "08/02/24",
        "itemImportance": 1
      } 
    ]
  }
}
