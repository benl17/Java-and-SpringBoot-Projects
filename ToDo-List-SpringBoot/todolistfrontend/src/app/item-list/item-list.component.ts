import { Component, OnInit } from '@angular/core';
import { Item } from '../item';
import { ItemService } from '../item.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrl: './item-list.component.css'
})
export class ItemListComponent implements OnInit {
  items!: Item[];

  constructor(private itemService: ItemService) { }

  ngOnInit(): void {
    this.getItems();
  }

  private getItems() {
    this.itemService.getItemsList().subscribe(data => {
      this.items = data;
    });
  }
}
