import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GoodsComponent } from './goods.component';
import { GoodsDetailComponent } from './goods-detail.component';
import { GoodsPopupComponent } from './goods-dialog.component';
import { GoodsDeletePopupComponent } from './goods-delete-dialog.component';

import { Principal } from '../../shared';


export const goodsRoute: Routes = [
  {
    path: 'goods',
    component: GoodsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Goods'
    }
  }, {
    path: 'goods/:id',
    component: GoodsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Goods'
    }
  }
];

export const goodsPopupRoute: Routes = [
  {
    path: 'goods-new',
    component: GoodsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Goods'
    },
    outlet: 'popup'
  },
  {
    path: 'goods/:id/edit',
    component: GoodsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Goods'
    },
    outlet: 'popup'
  },
  {
    path: 'goods/:id/delete',
    component: GoodsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Goods'
    },
    outlet: 'popup'
  }
];
