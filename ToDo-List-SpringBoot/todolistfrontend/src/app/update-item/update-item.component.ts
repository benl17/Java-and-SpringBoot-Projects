import { Component, OnInit } from '@angular/core';
import { Item } from '../item';
import { ItemService } from '../item.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-item',
  templateUrl: './update-item.component.html',
  styleUrl: './update-item.component.css'
})
export class UpdateItemComponent implements OnInit{
  
  id!: number;
  item: Item = new Item();

  constructor(private itemService: ItemService,
    private route: ActivatedRoute,
    private router: Router) {}
  
    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];

      this.itemService.getItemById(this.id).subscribe(data => {
        this.item = data;
      });
  }

  updateItem() {
    this.itemService.updateItem(this.id, this.item).subscribe( data => {
      console.log(data);
      this.goToItemList();
    });
  }

  goToItemList() {
    this.router.navigate(['/items']);
  }

  onSubmit() {
    console.log(this.item);
    this.updateItem();
  }
}
