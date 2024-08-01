import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ItemListComponent } from './item-list/item-list.component';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { CreateItemComponent } from './create-item/create-item.component';
import { FormsModule } from '@angular/forms';
import { UpdateItemComponent } from './update-item/update-item.component';

@NgModule({
  declarations: [
    AppComponent,
    ItemListComponent,
    CreateItemComponent,
    UpdateItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
