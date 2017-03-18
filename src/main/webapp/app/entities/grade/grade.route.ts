import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GradeComponent } from './grade.component';
import { GradeDetailComponent } from './grade-detail.component';
import { GradePopupComponent } from './grade-dialog.component';
import { GradeDeletePopupComponent } from './grade-delete-dialog.component';

import { Principal } from '../../shared';


export const gradeRoute: Routes = [
  {
    path: 'grade',
    component: GradeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Grades'
    }
  }, {
    path: 'grade/:id',
    component: GradeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Grades'
    }
  }
];

export const gradePopupRoute: Routes = [
  {
    path: 'grade-new',
    component: GradePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Grades'
    },
    outlet: 'popup'
  },
  {
    path: 'grade/:id/edit',
    component: GradePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Grades'
    },
    outlet: 'popup'
  },
  {
    path: 'grade/:id/delete',
    component: GradeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Grades'
    },
    outlet: 'popup'
  }
];
