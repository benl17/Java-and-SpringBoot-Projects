import { Component } from '@angular/core';
import { Item } from '../item';
import { ItemService } from '../item.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-item',
  templateUrl: './update-item.component.html',
  styleUrl: './update-item.component.css'
})
export class UpdateItemComponent {
  item: Item = new Item();

  constructor(private itemService: ItemService,
    private router: Router) {}
  ngOnInit(): void {
  }

  saveItem() {
    this.itemService.createItem(this.item).subscribe( data => {
      console.log(data);
      this.goToItemList();
    });
  }

  goToItemList() {
    this.router.navigate(['/items']);
  }

  onSubmit() {
    console.log(this.item);
    this.saveItem();
  }
}
