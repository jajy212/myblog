import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MemberCateComponent } from './member-cate.component';
import { MemberCateDetailComponent } from './member-cate-detail.component';
import { MemberCatePopupComponent } from './member-cate-dialog.component';
import { MemberCateDeletePopupComponent } from './member-cate-delete-dialog.component';

import { Principal } from '../../shared';


export const memberCateRoute: Routes = [
  {
    path: 'member-cate',
    component: MemberCateComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemberCates'
    }
  }, {
    path: 'member-cate/:id',
    component: MemberCateDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemberCates'
    }
  }
];

export const memberCatePopupRoute: Routes = [
  {
    path: 'member-cate-new',
    component: MemberCatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemberCates'
    },
    outlet: 'popup'
  },
  {
    path: 'member-cate/:id/edit',
    component: MemberCatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemberCates'
    },
    outlet: 'popup'
  },
  {
    path: 'member-cate/:id/delete',
    component: MemberCateDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MemberCates'
    },
    outlet: 'popup'
  }
];
