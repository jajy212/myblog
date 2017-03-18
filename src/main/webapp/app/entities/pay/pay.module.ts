import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JblogSharedModule } from '../../shared';

import {
    PayService,
    PayPopupService,
    PayComponent,
    PayDetailComponent,
    PayDialogComponent,
    PayPopupComponent,
    PayDeletePopupComponent,
    PayDeleteDialogComponent,
    payRoute,
    payPopupRoute,
} from './';

let ENTITY_STATES = [
    ...payRoute,
    ...payPopupRoute,
];

@NgModule({
    imports: [
        JblogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PayComponent,
        PayDetailComponent,
        PayDialogComponent,
        PayDeleteDialogComponent,
        PayPopupComponent,
        PayDeletePopupComponent,
    ],
    entryComponents: [
        PayComponent,
        PayDialogComponent,
        PayPopupComponent,
        PayDeleteDialogComponent,
        PayDeletePopupComponent,
    ],
    providers: [
        PayService,
        PayPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JblogPayModule {}
