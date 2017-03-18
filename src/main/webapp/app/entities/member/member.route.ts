import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MemberComponent } from './member.component';
import { MemberDetailComponent } from './member-detail.component';
import { MemberPopupComponent } from './member-dialog.component';
import { MemberDeletePopupComponent } from './member-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MemberResolvePagingParams implements Resolve<any> {

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

export const memberRoute: Routes = [
  {
    path: 'member',
    component: MemberComponent,
    resolve: {
      'pagingParams': MemberResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Members'
    }
  }, {
    path: 'member/:id',
    component: MemberDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Members'
    }
  }
];

export const memberPopupRoute: Routes = [
  {
    path: 'member-new',
    component: MemberPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Members'
    },
    outlet: 'popup'
  },
  {
    path: 'member/:id/edit',
    component: MemberPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Members'
    },
    outlet: 'popup'
  },
  {
    path: 'member/:id/delete',
    component: MemberDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Members'
    },
    outlet: 'popup'
  }
];
