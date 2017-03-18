import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GoodsTypeComponent } from './goods-type.component';
import { GoodsTypeDetailComponent } from './goods-type-detail.component';
import { GoodsTypePopupComponent } from './goods-type-dialog.component';
import { GoodsTypeDeletePopupComponent } from './goods-type-delete-dialog.component';

import { Principal } from '../../shared';


export const goodsTypeRoute: Routes = [
  {
    path: 'goods-type',
    component: GoodsTypeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'GoodsTypes'
    }
  }, {
    path: 'goods-type/:id',
    component: GoodsTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'GoodsTypes'
    }
  }
];

export const goodsTypePopupRoute: Routes = [
  {
    path: 'goods-type-new',
    component: GoodsTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'GoodsTypes'
    },
    outlet: 'popup'
  },
  {
    path: 'goods-type/:id/edit',
    component: GoodsTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'GoodsTypes'
    },
    outlet: 'popup'
  },
  {
    path: 'goods-type/:id/delete',
    component: GoodsTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'GoodsTypes'
    },
    outlet: 'popup'
  }
];
