import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MemOrderComponent } from './mem-order.component';
import { MemOrderDetailComponent } from './mem-order-detail.component';
import { MemOrderPopupComponent } from './mem-order-dialog.component';
import { MemOrderDeletePopupComponent } from './mem-order-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MemOrderResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const memOrderRoute: Routes = [
  {
    path: 'mem-order',
    component: MemOrderComponent,
    resolve: {
      'pagingParams': MemOrderResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemOrders'
    }
  }, {
    path: 'mem-order/:id',
    component: MemOrderDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemOrders'
    }
  }
];

export const memOrderPopupRoute: Routes = [
  {
    path: 'mem-order-new',
    component: MemOrderPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemOrders'
    },
    outlet: 'popup'
  },
  {
    path: 'mem-order/:id/edit',
    component: MemOrderPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemOrders'
    },
    outlet: 'popup'
  },
  {
    path: 'mem-order/:id/delete',
    component: MemOrderDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemOrders'
    },
    outlet: 'popup'
  }
];
