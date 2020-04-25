import { RouterModule,Route } from "@angular/router";
import { NgModule } from '@angular/core';

const routes: Route[] = [];

@NgModule({
    imports: [ RouterModule.forRoot(routes, {
      onSameUrlNavigation: 'reload'
    })],
    exports: [ RouterModule ],
  })
  export class AppRoutingModule {}