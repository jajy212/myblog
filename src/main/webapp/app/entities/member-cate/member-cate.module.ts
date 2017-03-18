import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JblogSharedModule } from '../../shared';

import {
    MemberCateService,
    MemberCatePopupService,
    MemberCateComponent,
    MemberCateDetailComponent,
    MemberCateDialogComponent,
    MemberCatePopupComponent,
    MemberCateDeletePopupComponent,
    MemberCateDeleteDialogComponent,
    memberCateRoute,
    memberCatePopupRoute,
} from './';

let ENTITY_STATES = [
    ...memberCateRoute,
    ...memberCatePopupRoute,
];

@NgModule({
    imports: [
        JblogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MemberCateComponent,
        MemberCateDetailComponent,
        MemberCateDialogComponent,
        MemberCateDeleteDialogComponent,
        MemberCatePopupComponent,
        MemberCateDeletePopupComponent,
    ],
    entryComponents: [
        MemberCateComponent,
        MemberCateDialogComponent,
        MemberCatePopupComponent,
        MemberCateDeleteDialogComponent,
        MemberCateDeletePopupComponent,
    ],
    providers: [
        MemberCateService,
        MemberCatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JblogMemberCateModule {}
