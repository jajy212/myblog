import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PayComponent } from './pay.component';
import { PayDetailComponent } from './pay-detail.component';
import { PayPopupComponent } from './pay-dialog.component';
import { PayDeletePopupComponent } from './pay-delete-dialog.component';

import { Principal } from '../../shared';


export const payRoute: Routes = [
  {
    path: 'pay',
    component: PayComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pays'
    }
  }, {
    path: 'pay/:id',
    component: PayDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pays'
    }
  }
];

export const payPopupRoute: Routes = [
  {
    path: 'pay-new',
    component: PayPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pays'
    },
    outlet: 'popup'
  },
  {
    path: 'pay/:id/edit',
    component: PayPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pays'
    },
    outlet: 'popup'
  },
  {
    path: 'pay/:id/delete',
    component: PayDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Pays'
    },
    outlet: 'popup'
  }
];
